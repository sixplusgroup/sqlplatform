package com.qiao.spring.util;

import com.qiao.spring.pojo.Table;

public class BodyStructure {
    private String query;
    private Table[] tables;
    private String coverageRequestXML;

    public BodyStructure(String query, Table[] tables, String coverageRequestXML) {
        this.query = query;
        this.tables = tables;
        this.coverageRequestXML = coverageRequestXML;
    }
    public Table[] getTables() {
        return tables;
    }

    public void setTables(Table[] tables) {
        this.tables = tables;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCoverageRequestXML() {
        return coverageRequestXML;
    }

    public void setCoverageRequestXML(String coverageRequestXML) {
        this.coverageRequestXML = coverageRequestXML;
    }

}
