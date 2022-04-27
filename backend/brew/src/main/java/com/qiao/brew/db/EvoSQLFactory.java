package com.qiao.brew.db;

import com.qiao.ga.EvoSQL;

/**
 * A class for creating instances of EvoSQL classes.
 */
public class EvoSQLFactory {
    /**
     * Creates a new instance of the EvoSQL class.
     * @return A new instance of the EvoSQL class.
     */
    public EvoSQL createEvoSQL() {
        return new EvoSQL(false);
    }
}
