package com.qiao.ga.metaheuristics.operators;

import com.qiao.ga.fixture.Fixture;
import com.qiao.ga.fixture.FixtureTable;
import com.qiao.ga.random.Randomness;

import java.util.ArrayList;
import java.util.List;

public class FixtureCrossover {

	private static final Randomness random = new Randomness();

	/**
	 * Performs scattered crossover
	 * @return a list with the two new offspring
	 */
	public static Fixture[] crossover(Fixture parent1, Fixture parent2) {

		if (parent1.getTables().size() < 1 || parent2.getTables().size() < 1 )
			throw new IllegalArgumentException("Each solution must have at least one Table");

		Fixture[] offspring = new Fixture[2];
		List<FixtureTable> offspring1Tables = new ArrayList<>();
		List<FixtureTable> offspring2Tables = new ArrayList<>();

		if (isApplicable(parent1, parent2)) {
			// probability to send it to left or right is also random
			double probability = random.nextDouble();

			for (FixtureTable tableFromP1 : parent1.getTables()) {
				// i do not trust on the unordered list, so that's why i' using table name and not indexes
				FixtureTable tableFromP2 = parent2.getTable(tableFromP1.getName());

				boolean left = random.nextDouble() < probability;
				offspring1Tables.add(left ? tableFromP1.copy() : tableFromP2.copy());
				offspring2Tables.add(left ? tableFromP2.copy() : tableFromP1.copy());
			}

			offspring[0] = new Fixture(offspring1Tables);
			offspring[0].setChanged(true);
			offspring[1] = new Fixture(offspring2Tables);
			offspring[1].setChanged(true);
		} else {
			offspring[0] = parent1.copy();
			offspring[1] = parent2.copy();
		}

		return offspring;

	}

	/**
	 * The crossover can be applied only to Fixture with at least two Table each
	 * @param parent1 fixture parent 1
	 * @param parent2 fixture parent 2
	 * @return true if the parents contain at least two Tables
	 */
	protected static boolean isApplicable(Fixture parent1, Fixture parent2) {
		return (parent1.getNumberOfTables()>1 && parent2.getNumberOfTables()>1);
	}
}
