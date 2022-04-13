package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.MainQuestionMapper;
import com.example.sqlexercise.data.SubQuestionMapper;
import com.example.sqlexercise.po.MainQuestion;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.service.SqlDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class SqlDatabaseServiceImpl implements SqlDatabaseService {


    @Autowired
    private MainQuestionMapper mainQuestionMapper;
    @Autowired
    private SubQuestionMapper subQuestionMapper;

    @Override
    public String getSchemaNameByMainId(int mainId){
        if(mainQuestionMapper.getByMainId(mainId)==null){
            System.out.println("Info of main question "+mainId+" is not found!");
            return "error!";
        }
        return "main_"+mainId;
    }

    @Override
    public String getSchemaConstructorByMainId(int mainId){
        MainQuestion mainQuestion = mainQuestionMapper.getByMainId(mainId);
        if(mainQuestion==null){
            System.out.println("Info of main question "+mainId+" is not found!");
        }
        try {
            Path dbPath = Paths.get(mainQuestion.getDbPath());
            if (!Files.exists(dbPath)) {
                System.out.println("File of main question" + mainId + "is not found!");
            }
            return Files.readString(dbPath);
        }catch (Exception e){
            e.printStackTrace();
            return "IOException occurred!";
        }
    }

    @Override
    public SubQuestion getSubQuestionBySubId(int subId){
        return subQuestionMapper.getBySubId(subId);
    }

    @Override
    public String runSqlTask(int mainId, String driver, String sqlText, Map options){
        int maxRetryTimes = Integer.parseInt(options.getOrDefault("maxRetryTimes", 1).toString());
        boolean forEach = Boolean.parseBoolean(options.getOrDefault("forEach", false).toString());
        boolean skipPre = Boolean.parseBoolean(options.getOrDefault("skipPre", false).toString());
        boolean skipPost = Boolean.parseBoolean(options.getOrDefault("skipPost", false).toString());
        String schemaName = getSchemaNameByMainId(mainId);
        String schemaConstructor = getSchemaConstructorByMainId(mainId);

        if(forEach){

        }

        return "";
    }

    @Override
    public String getStandardAnswer(int subId, String driver){
        return "4";
    }

    @Override
    public Object runTaskOfGettingSchemaInfo(int mainId, String driver){
        return new Object();
    }

    @Override
    public Object runTaskOfUpdatingSchemaInfo(int mainId, String driver){
        return new Object();
    }

    @Override
    public Object runTaskOfCleaningSchema(int mainId, String driver){
        return new Object();
    }

}
