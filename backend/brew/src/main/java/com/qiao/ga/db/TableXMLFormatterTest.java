package com.qiao.ga.db;

//import org.mockito.Mockito;


//import static org.mockito.ArgumentMatchers.any;

public class TableXMLFormatterTest {
//
//    private ISchemaExtractor schemaExtractor;
//    private TableXMLFormatter tableXMLFormatter;
//    static final String QUERY = "SELECT username, email FROM users WHERE username='test user'";
//
//    @Before
//    public void setup() {
//        schemaExtractor = Mockito.mock(SchemaExtractor.class);
//        Map<String, TableSchema> schemaMap = new HashMap<>();
//        List<ColumnSchema> columnSchemas = new ArrayList<>();
//        TableSchema tableSchema = new TableSchema("users", columnSchemas);
//        columnSchemas.add(new ColumnSchema(tableSchema, "username", new DBString(500), false, false));
//        columnSchemas.add(new ColumnSchema(tableSchema, "email", new DBString(500), false, false));
//        schemaMap.put("test", tableSchema);
//        Mockito.when(schemaExtractor.getTablesFromQuery(any(String.class))).thenReturn(schemaMap);
//    }
//
//    @Test
//    public void queryTest() {
//        tableXMLFormatter = new TableXMLFormatter(schemaExtractor, QUERY);
//        String expected =   "<schema>" +
//                                "<table name=\"users\">" +
//                                    "<column name=\"username\" type=\"varchar\" notnull=\"true\"/>" +
//                                    "<column name=\"email\" type=\"varchar\" notnull=\"true\"/>" +
//                                "</table>" +
//                            "</schema>";
//        String actual = tableXMLFormatter.getSchemaXml();
//        Assert.assertEquals(expected, actual);
//    }
}
