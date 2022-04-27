package com.qiao.brew.sql.vendor;

public class MySQLOptions implements VendorOptions {
    @Override
    public String escapeIdentifier(String id) {
        return "`" + id + "`";
    }
}
