package com.qiao.ga.fixture.type;

import com.qiao.ga.db.Seeds;

import java.io.Serializable;

public interface DBType extends Serializable {
	String generateRandom(boolean nullable);
	String generateFromSeed(Seeds seeds);
	String mutate(String currentValue, boolean nullable);
	String getTypeString();
	String getNormalizedTypeString();
	boolean hasSeed(Seeds seeds);
}
