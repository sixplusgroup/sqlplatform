package com.qiao.spring.pojo;

//import com.github.sergdelft.sqlcorgi.schema.Column.DataType;
/**
 * Column contains all relevant database column data.
 */
public class Column {
    private String name;
    private boolean isNullable;
    private boolean isKey;
    private FE_DATATYPE dataType;

    /**
     * Data type enum.
     * Contains all supported data types.
     */
    public enum FE_DATATYPE {
        INTEGER,
        VARCHAR,
        BOOLEAN,
        DOUBLE,
        DATE,
        TIME,
        TIMESTAMP,
        NULL,
    }

    /**
     * Constructor to instantiate a column.
     * @param name Column name.
     * @param isNullable Specifies whether the it is nullable.
     * @param isKey Specifies whether it is a key.
     * @param dataType The data type.
     */
    public Column(String name, boolean isNullable, boolean isKey, FE_DATATYPE dataType) {
        this.name = name;
        // 因为前端传的是 isNotNull 的数据，所以这里要取反。取反了就能正常的生成 null 数据了，否则就不行。
        this.isNullable = !isNullable;
        this.isKey = isKey;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public boolean isKey() {
        return isKey;
    }

    public FE_DATATYPE getDataType() {
        return dataType;
    }
}
