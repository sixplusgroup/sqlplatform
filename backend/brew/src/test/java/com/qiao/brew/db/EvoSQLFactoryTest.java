package com.qiao.brew.db;

import com.qiao.ga.EvoSQL;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EvoSQLFactoryTest {
    @Test
    void createEvoSQLTest() {
        ConnectionData connectionData = new ConnectionData("cs", "db", "user", "pass");

        EvoSQLFactory evoSQLFactory = new EvoSQLFactory();
        assertThat(new EvoSQL(false)).isInstanceOf(EvoSQL.class);
    }
}
