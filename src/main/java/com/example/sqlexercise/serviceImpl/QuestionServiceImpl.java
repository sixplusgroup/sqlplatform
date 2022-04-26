package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.MainQuestionMapper;
import com.example.sqlexercise.data.SubQuestionMapper;
import com.example.sqlexercise.po.MainQuestion;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final MainQuestionMapper mainQuestionMapper;
    private final SubQuestionMapper subQuestionMapper;

    @Autowired
    public QuestionServiceImpl(MainQuestionMapper mainQuestionMapper, SubQuestionMapper subQuestionMapper) {
        this.mainQuestionMapper = mainQuestionMapper;
        this.subQuestionMapper = subQuestionMapper;
    }

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
            Path dbPath = Paths.get("src/main/resources/"+mainQuestion.getDbPath());
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
    public MainQuestion getMainQuestionByMainId(int mainId) {
        return mainQuestionMapper.getByMainId(mainId);
    }
}
