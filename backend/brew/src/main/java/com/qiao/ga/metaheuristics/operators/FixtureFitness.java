package com.qiao.ga.metaheuristics.operators;

import genetic.QueryLevelData;

import java.util.Objects;

/**
 * Fitness of one fixture
 *
 */
public class FixtureFitness {
	
	QueryLevelData lastLevelData;
	
	public FixtureFitness(QueryLevelData lastLevelData) {
		this.lastLevelData = lastLevelData;
	}

	public QueryLevelData getQueryLevelData(int queryLevel) {
		if (queryLevel > lastLevelData.getQueryLevel() || queryLevel < 0)
			throw new IndexOutOfBoundsException();
		
		QueryLevelData qld = lastLevelData;
		while (qld != null && qld.getQueryLevel() > queryLevel) {
			qld = qld.getPrevLevelData();
		}

		return qld;
	}
	
	public int getMaxQueryLevel() {
		return lastLevelData.getQueryLevel();
	}

	public int getMaxRangeVariableIndex(int i) {
		if (i > lastLevelData.getQueryLevel() || i < 0)
			throw new IndexOutOfBoundsException();
		
		QueryLevelData qld = lastLevelData;
		while (qld != null && qld.getQueryLevel() > i) {
			qld = qld.getPrevLevelData();
		}
		if (qld == null)
			return 0;
		return qld.getMaxRangeVariableIndex();
	}

	public double getDistance(int i) {
		if (i > lastLevelData.getQueryLevel() || i < 0)
			throw new IndexOutOfBoundsException();
		
		QueryLevelData qld = lastLevelData;
		while (qld != null && qld.getQueryLevel() > i) {
			qld = qld.getPrevLevelData();
		}
		if (qld == null)
			return 0;
		return qld.getDistance();
	}
	
	// Returns the last distance, which can be checked to see if this is a solution
	public double getDistance() {
		if (lastLevelData == null)
			return 0;
		
		return lastLevelData.getDistance();
	}
	
	public FixtureFitness copy() {
		return new FixtureFitness(lastLevelData.copy());
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("{");
		QueryLevelData qld = lastLevelData;
		while (qld != null) {
			result.append(qld);
			qld = qld.getPrevLevelData();
			if (qld != null)
				result.append(", ");
		}
		result.append("}");
		return result.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FixtureFitness that = (FixtureFitness) o;

		return Objects.equals(lastLevelData, that.lastLevelData);
	}

	@Override
	public int hashCode() {
		return lastLevelData != null ? lastLevelData.hashCode() : 0;
	}
}

