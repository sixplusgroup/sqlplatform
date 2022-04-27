package com.qiao.ga.db;

import com.github.sergdelft.sqlcorgi.schema.Column;
import com.github.sergdelft.sqlcorgi.schema.Column.DataType;
import com.github.sergdelft.sqlcorgi.schema.Schema;
import com.github.sergdelft.sqlcorgi.schema.Table;
import com.qiao.ga.fixture.type.*;
import com.qiao.ga.sql.ColumnSchema;
import com.qiao.ga.sql.TableSchema;
import com.qiao.ga.fixture.type.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to convert a {@link TableSchema} to a {@link Schema} using the input query.
 * Conversion is needed to allow SQLCoRGi to use the Schema.
 */
public class SchemaConverter {
    private com.qiao.spring.pojo.Table[] tables;
    public SchemaConverter(com.qiao.spring.pojo.Table[] tables) {
        this.tables = tables;
    }

    /**
     * Retrieves the {@link Schema} from the input query using the {@link SchemaExtractor}.
     * @return The schema obtained from the {@code query}.
     */
    public Schema getSchema() {
        Map<String, TableSchema> tableSchemas = getTableSchemas();
        Schema schema = new Schema();
        for (TableSchema tableSchema : tableSchemas.values()) {
            schema.addTable(new Table(tableSchema.getName(), convertColumns(tableSchema.getColumns())));
        }
        return schema;
    }
    // 仿照 DBTypeSelector 类里的 create 方法。
    private DBType feTypeToCsType(com.qiao.spring.pojo.Column.FE_DATATYPE feDatatype) {
        switch (feDatatype) {
            case INTEGER:
                return new DBInteger();
            case VARCHAR:
                return new DBString(100);
            case DATE:
                return new DBDate();
            case BOOLEAN:
                return new DBBoolean("BOOLEAN");
            case TIME:
                return new DBTime();
            case TIMESTAMP:
                return new DBTime("TIMESTAMP");
            case DOUBLE:
                return new DBDouble();
            default:
                throw new UnsupportedOperationException("This database type is not currently supported: " + feDatatype);
        }
    }
    public Map<String , TableSchema> getTableSchemas() {
        Map<String, TableSchema> tableSchemas = new HashMap<>();
        for (com.qiao.spring.pojo.Table table : this.tables) {
            List<ColumnSchema> parsedColumns = new ArrayList<>();
            TableSchema ts = new TableSchema(table.getName(), parsedColumns);
            for (com.qiao.spring.pojo.Column column : table.getColumns()) {
                parsedColumns.add(new ColumnSchema(ts, column.getName(), feTypeToCsType(column.getDataType()), column.isNullable(), false));
            }
            tableSchemas.put(table.getName(), ts);
        }
        return tableSchemas;
    }

    /**
     * Converts a list of ColumnSchemas to a list of Columns.
     *
     * @param columnSchemas The list of column schemas to be converted.
     * @return A list of Columns obtained from the column schemas.
     */
    private List<Column> convertColumns(List<ColumnSchema> columnSchemas) {
        List<Column> columns = new ArrayList<>();
        for (ColumnSchema columnSchema : columnSchemas) {
            columns.add(new Column(columnSchema.getName(), columnSchema.isNullable(),
                    false, convertType(columnSchema.getType())));
        }

        return columns;
    }

    /**
     * Returns the correct DataType corresponding to the DBType.
     * @param type The type to be converted.
     * @return The correct DataType based on the DBType.
     */
    private DataType convertType(DBType type) {
        if (type instanceof DBInteger || type instanceof DBDouble) {
            return DataType.NUM;
        } else {
            return DataType.STRING;
        }
    }
}
