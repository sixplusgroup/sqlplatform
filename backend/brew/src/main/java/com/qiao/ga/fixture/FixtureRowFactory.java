package com.qiao.ga.fixture;

import java.util.List;
import java.util.NoSuchElementException;

import com.qiao.ga.EvoSQLConfiguration;
import com.qiao.ga.db.Seeds;
import com.qiao.ga.sql.TableSchema;
import com.qiao.ga.sql.ColumnSchema;
import com.qiao.ga.random.Randomness;

public class FixtureRowFactory {
	private Randomness random;
	private double probability;
	
	public FixtureRowFactory(Randomness random, double probability) {
		this.random = random;
		this.probability = probability;
	}

	public FixtureRowFactory() {
		this.random = new Randomness();
		this.probability = EvoSQLConfiguration.SEED_INSERT_PROBABILITY;
	}
	
	public FixtureRow create(TableSchema tableSchema, List<FixtureTable> tables, Seeds seeds) {
		FixtureRow row = new FixtureRow(tableSchema.getName(), tableSchema);
		
		// for each column, find a random value
		for(ColumnSchema column : tableSchema.getColumns()) {
			if (seeds != null && (column.getType().hasSeed(seeds) || column.getSeedSourceColumns() != null) 
					&& random.nextDouble() <= probability) {
				
				// Dynamic seeding
				if (EvoSQLConfiguration.USE_DYNAMIC_JOIN_SEEDING && column.getSeedSourceColumns() != null) {
					for (ColumnSchema seedCS : column.getSeedSourceColumns()) {
						// Get FixtureTable
						FixtureTable ft;
						try {
							ft = tables.stream().filter(ftlocal -> ftlocal.getSchema() == seedCS.getTable()).findFirst().get();
						} catch (NoSuchElementException e) {
							continue;
						}
						seeds.addToTemp(ft.getColumnValues(seedCS));
					}
				}
				
				// Only use seeded value if not null
				String seedValue = column.getType().generateFromSeed(seeds);
				seeds.unsetTemp();
				if (seedValue != null) {
					row.set(column.getName(), seedValue);
					continue;
				}
			}
			
			fillRandom(row, column);
		}
		
		return row;
	}

	private void fillRandom(FixtureRow row, ColumnSchema column) {
		String generatedValue = column.getType().generateRandom(column.isNullable());
		row.set(column.getName(), generatedValue);
	}

}
