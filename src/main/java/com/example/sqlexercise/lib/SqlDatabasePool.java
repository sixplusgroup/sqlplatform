package com.example.sqlexercise.lib;

import com.fasterxml.uuid.Generators;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SqlDatabasePool {

    int count;
    String[] drivers;
    Map<String, ItemOfSqlDatabaseMap> sqlDatabaseMap;
    int maxRows;
    ArrayList<DockerServer> dockerServers;
    ArrayList<String> servers;


    private final String NAMESPACE_URL = "6ba7b811-9dad-11d1-80b4-00c04fd430c8";

    public SqlDatabasePool(String[] drivers, Map<String, ItemOfSqlDatabaseMap> sqlDatabaseMap){
        this.drivers = drivers;
        this.sqlDatabaseMap = sqlDatabaseMap;
        this.dockerServers = DockerServer.getDockerServers();
        this.maxRows = 500;
        ArrayList<String> servers = new ArrayList<>();
        for(DockerServer dockerServer : dockerServers){
            servers.add(dockerServer.id);
        }
        this.servers = servers;
    }

    public SqlDatabasePool(String[] drivers){
        this.drivers = drivers;
        this.sqlDatabaseMap = new HashMap<>();
        this.dockerServers = DockerServer.getDockerServers();
        this.maxRows = 500;
        ArrayList<String> servers = new ArrayList<>();
        for(DockerServer dockerServer : dockerServers){
            servers.add(dockerServer.id);
        }
        this.servers = servers;
    }

    private ItemOfSqlDatabaseMap createItemOfMap(@NotNull String schemaName){
        //create uuidv5 namespace
        UUID namespace = Generators.nameBasedGenerator(UUID.fromString(NAMESPACE_URL)).generate("sqlexercise");
        ItemOfSqlDatabaseMap inRoot = null;
        if(!schemaName.isEmpty()){
            inRoot = getItemOfMap("");
        }
        ItemOfSqlDatabaseMap inSchema = new ItemOfSqlDatabaseMap();
        for(String driver: this.drivers){
            Map inDriver = new HashMap<String, ArrayList<SqlDatabase>>();
            for(String server: this.servers){
                ArrayList<SqlDatabase> inServer = new ArrayList<>();
                for(DockerServer e: this.dockerServers){
                    if(e.id.equals(server)){
                        for(int i=0; i<e.container; i++){
                            SqlDatabaseConfig config = new SqlDatabaseConfig();
                            config.tags = new HashMap<>();
                            config.tags.put("schemaName",schemaName);
                            config.tags.put("driver", driver);
                            config.tags.put("server", server);
                            config.tags.put("index", i);
                            config.host = e.host;
                            config.port = 3310+i;
                            config.username = schemaName.isEmpty() ? "sqlexercise" : "root";
                            config.password = Generators.nameBasedGenerator(namespace).generate(driver+"-"+server+"-"+i).toString();
                            config.maxRows = this.maxRows;

                            SqlDatabase root = null;
                            if(!schemaName.isEmpty()){
                                root = inRoot.itemOfSqlDatabaseMap.get(driver).get(server).get(i);
                            }
                            inServer.add(new SqlDatabase(config, root));
                        }
                        break;
                    }
                }
                inDriver.put(server, inServer);
            }
            inSchema.itemOfSqlDatabaseMap.put(driver, inDriver);
        }
        this.sqlDatabaseMap.put(schemaName, inSchema);
        return inSchema;
    }

    private ItemOfSqlDatabaseMap getItemOfMap(String schemaName){
        // 若不存在某个 Schema 的结构，则先进行构造
        if(!this.sqlDatabaseMap.containsKey(schemaName)){
            // 此处先赋值为构造的异步任务，方法内部会再自动赋值为构造的结果
            this.sqlDatabaseMap.put(schemaName, createItemOfMap(schemaName));
        }
        // 不论此处是异步任务还是结果，最终都将统一给出异步结果
        return this.sqlDatabaseMap.get(schemaName);
    }

    public SqlDatabase getSqlDatabase(String schemaName, String driver, String server, int index){
        ItemOfSqlDatabaseMap item = getItemOfMap(schemaName);
        return item.itemOfSqlDatabaseMap.get(driver).get(server).get(index);
    }

    private ArrayList<SqlDatabase> getSqlDatabaseList(String schemaName, String driver){
        ItemOfSqlDatabaseMap item = getItemOfMap(schemaName);
        ArrayList<SqlDatabase> sqlDatabases = new ArrayList<>();
        for(String server: this.servers){
            sqlDatabases.addAll(item.itemOfSqlDatabaseMap.get(driver).get(server));
        }
        return sqlDatabases;
    }

    public SqlDatabase pickSqlDatabase(String schemaName, String driver){
        SqlDatabase sqlDatabase = null;
        ArrayList<SqlDatabase> sqlDatabases = getSqlDatabaseList(schemaName, driver);
        int length = sqlDatabases.size();
        while (sqlDatabase==null || sqlDatabase.lockedQueries > 0){
            if(sqlDatabase!=null){
                sqlDatabase.unlock();
            }
            sqlDatabase = sqlDatabases.get(this.count % length);
            this.count++;
        }
        return sqlDatabase;
    }


}
