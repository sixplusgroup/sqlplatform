package com.qiao.ga.metaheuristics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.qiao.ga.EvoSQLConfiguration;
import com.qiao.ga.db.Seeds;
import com.qiao.ga.fixture.Fixture;
import com.qiao.ga.fixture.FixtureRow;
import com.qiao.ga.fixture.FixtureRowFactory;
import com.qiao.ga.fixture.FixtureTable;
import com.qiao.ga.metaheuristics.operators.FixtureComparator;
import com.qiao.ga.metaheuristics.operators.FixtureCrossover;
import com.qiao.ga.metaheuristics.operators.FixtureFitness;
import com.qiao.ga.metaheuristics.operators.FixtureMutation;
import com.qiao.ga.metaheuristics.operators.TournamentSelection;
import com.qiao.ga.sql.TableSchema;
import com.qiao.ga.random.Randomness;

public class StandardGA extends Approach {
	private boolean isInitialized;

	/** Current Population **/
	private List<Fixture> population;

	/** Row Factory **/
	private final FixtureRowFactory rowFactory = new FixtureRowFactory();

	/** Mutation operator **/
	private final FixtureMutation mutation;

	/** Comparator **/
	private final FixtureComparator fc = new FixtureComparator();

	/** Seeds store **/
	private final Seeds seeds;

	private final int populationSize;

	public StandardGA(List<Fixture> population, Map<String, TableSchema> pTableSchemas, String pPathToBeTested, Seeds seeds){
		super(pTableSchemas, pPathToBeTested);

		this.seeds = seeds;

		this.mutation = new FixtureMutation(rowFactory, seeds);
		this.population = population;
		this.isInitialized = false;

		// if it's baseline, there will be only a single generation, and population will be larger
		this.populationSize = EvoSQLConfiguration.POPULATION_SIZE; // * (baseline ? 2 : 1);
	}

	private void initialize() throws SQLException {
		//1. Initial population
		generateInitialPopulation();

		calculateFitness(population);

		// 2. sort population by Fitness Function
		population.sort(fc);
		log.debug("Best Fitness Function in the generated population = {}", population.get(0).getFitness());

		isInitialized = true;
	}

	@Override
	public Fixture execute(long pathTime) throws SQLException {
		long startTime = System.currentTimeMillis();

		// Initialize first
		if (!isInitialized) {
			initialize();
		}

		//3. Main Loop
		while (population.get(0).getFitness().getDistance() > 0
				&& System.currentTimeMillis() - startTime < pathTime
//				&& generations < maxGenerations
		){
			List<Fixture> offspring_population = new ArrayList<>(populationSize);
			for (int index=0; index < populationSize; index += 2){
				// Get two parents through selection operator
				Fixture parent1 = TournamentSelection.getWinnerFixture(population);
				Fixture parent2 = TournamentSelection.getWinnerFixture(population);

				Fixture offspring1;
				Fixture offspring2;

				// Use the crossover operator，交叉的概率为 75%，不发生交叉时，子代直接 copy 父代。
			    if (random.nextDouble() < EvoSQLConfiguration.P_CROSSOVER) {

					Fixture[] generatedOffspring = FixtureCrossover.crossover(parent1, parent2);
					offspring1 = generatedOffspring[0];
					offspring2 = generatedOffspring[1];

			        log.debug("Crossover applied");
			    } else {
					offspring1 = parent1.copy();
					offspring2 = parent2.copy();
				}

			    // Mutate 交叉完了做变种
			    mutation.mutate(offspring1);
			    mutation.mutate(offspring2);

			    // Calculate fitness and add offspring to the offspring_population if needed
			    if (offspring1.isChanged()) {
			    	calculateFitness(offspring1);
					log.debug("Fitness = {}", offspring1.getFitness());

					// we add only changed solutions (to avoid clones in the new population)
					offspring_population.add(offspring1);
			    }
			    if (offspring2.isChanged()) {
			    	calculateFitness(offspring2);
					log.debug("Fitness = {}", offspring2.getFitness());

					// we add only changed solutions (to avoid clones in the new population)
					offspring_population.add(offspring2);
			    }
			}

			// Combine original and offspring population
			// 将新生代和老生代的所有个体合并，然后取其中适应度最低的 populationSize 个，作为新的种群。
			List<Fixture> union = new ArrayList<>();
			union.addAll(population);
			union.addAll(offspring_population);

			// Order by fitness
			union.sort(fc);

			// Set the new population as the POPULATION_SIZE best
			population = union.subList(0, populationSize);

			log.debug("Generation = {}, best Fitness Function = {}", generations, population.get(0).getFitness());// + " for fixture: " + population.get(0));
			generations++;
		}

		log.info("Total generations: {}", generations);
		log.debug("Best Fitness Function = {}", population.get(0).getFitness());

		return minimize(population.get(0));

	}

	private void generateInitialPopulation() {
		int currentPopulationSize = 0;
		Random rand = new Random();
		List<Fixture> newPopulation = new ArrayList<>();
		// If we have a previous population fed by EvoSQL, clone some of these
		if (population != null && !population.isEmpty()) {
			// Order by fitness (the previous population should have fitness values)
			population.sort(new FixtureComparator());

			// Select random individuals
			boolean includesSolution = false;
			for (Fixture fixture : population) {
				if ((fixture.getFitness() != null && fixture.getFitness().getDistance() == 0 && !includesSolution) || // Always add one solution if there is one
						rand.nextDouble() <= EvoSQLConfiguration.P_CLONE_POPULATION) {
					if (fixture.getFitness() != null && fixture.getFitness().getDistance() == 0)
						includesSolution = true;
					// Get a copy of this fixture that matches the current path
					Fixture newFixture = fixture.copy();
					newFixture.setFitness(null); // make sure fitness is gone
					fixFixture(newFixture); // make sure all tables are present in the individual, and no more
					newPopulation.add(newFixture);
					currentPopulationSize++;
				}
			}
		}

		log.debug("Generating random initial population...");

		for(; currentPopulationSize < populationSize; currentPopulationSize++) {
			List<FixtureTable> tables = new ArrayList<>();
			for (TableSchema tableSchema : tableSchemas.values()) {
				tables.add(createFixtureTable(tableSchema, tables));
			}

			Fixture fixture = new Fixture(tables);
			log.debug("Fixture created: {}", fixture);
			newPopulation.add(fixture);
		}
		log.debug("Generated random population with {} fixtures", newPopulation.size());

		// Store the new population in the list given by EvoSQL
		population.clear();
		population.addAll(newPopulation);
	}

	private void calculateFitness(List<Fixture> solutions) throws SQLException{
		for(Fixture fixture : population) {
			calculateFitness(fixture);
		}
	}

	private void calculateFitness(Fixture fixture) throws SQLException {
		individualCount++;

		// Truncate tables in Instrumented DB
		for (TableSchema tableSchema : tableSchemas.values()) {
			genetic.Instrumenter.execute(tableSchema.getTruncateSQL());
		}

		// Insert population
		for (String sqlStatement : fixture.getInsertStatements()) {
			genetic.Instrumenter.execute(sqlStatement);
		}

		// Start instrumenter
		genetic.Instrumenter.startInstrumenting();

		// Execute the path
		genetic.Instrumenter.execute(pathToTest);

		FixtureFitness ff = new FixtureFitness(genetic.Instrumenter.getFitness());
		fixture.setFitness(ff);

		// Store exceptions
		if (!genetic.Instrumenter.getException().isEmpty() && !exceptions.contains(genetic.Instrumenter.getException())) {
			exceptions += ", " + genetic.Instrumenter.getException();
		}

		// Stop instrumenter
		genetic.Instrumenter.stopInstrumenting();

		// set the fixture as "not changed" to avoid future fitness function computation
		fixture.setChanged(false);
	}

	private FixtureTable createFixtureTable(TableSchema tableSchema, List<FixtureTable> tables) {
		List<FixtureRow> rows = new ArrayList<>();
		int numberOfRows = EvoSQLConfiguration.MIN_ROW_QTY;
		if (EvoSQLConfiguration.MAX_ROW_QTY > EvoSQLConfiguration.MIN_ROW_QTY)
			numberOfRows += random.nextInt(EvoSQLConfiguration.MAX_ROW_QTY - EvoSQLConfiguration.MIN_ROW_QTY);
		for(int j=0; j < numberOfRows; j++) {
			FixtureRow row = rowFactory.create(tableSchema, tables, seeds);
			rows.add(row);
			log.debug("Row created: {}", row);
		}
		return new FixtureTable(tableSchema, rows);
	}

	// Takes a fixture and removes all tables that are not in the current problem
	// Adds new FixtureTables for tables that are in the problem but not in the given fixture
	private void fixFixture(Fixture fixture) {
		List<FixtureTable> tables = fixture.getTables();
		int tableCount = tables.size();

		for (int i = 0; i < tables.size(); i++) {
			// Remove table if not in tableSchemas
			if (!tableSchemas.containsKey(tables.get(i).getName())) {
				fixture.removeTable(i);
				tableCount--;
			}
		}

		// If we have too few tables now, we need to add the missing ones
		if (tableCount < tableSchemas.size()) {
			for (TableSchema ts : tableSchemas.values()) {
				boolean present = false;
				for (FixtureTable ft : tables) {
					if (ft.getSchema().equals(ts)) {
						present = true;
						break;
					}
				}
				// Create a new fixture table if this schema is not present
				if (!present) {
					fixture.addTable(createFixtureTable(ts, tables));
				}
			}
		}
	}
}
