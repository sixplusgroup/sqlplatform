package com.qiao.brew.data;

import lombok.Data;
import com.qiao.ga.fixture.type.DBType;

/**
 * Represents a single column in the fixture.
 */
@Data
public class FixtureColumn {
    /**
     * Name of the column.
     */
    private final String name;
    /**
     * Name of the type.
     * @see DBType#getTypeString()
     */
    private final String type;
}
