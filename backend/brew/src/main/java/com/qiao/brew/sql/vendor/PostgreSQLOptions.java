package com.qiao.brew.sql.vendor;

public class PostgreSQLOptions implements VendorOptions {
    @Override
    public String escapeIdentifier(String id) {
        return "\"" + id + "\"";
    }
}
