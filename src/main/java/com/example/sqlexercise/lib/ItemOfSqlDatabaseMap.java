package com.example.sqlexercise.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemOfSqlDatabaseMap {

    Map<String, Map<String, ArrayList<SqlDatabase>>> itemOfSqlDatabaseMap;

    public ItemOfSqlDatabaseMap(){
        this.itemOfSqlDatabaseMap = new HashMap<>();
    }

}
