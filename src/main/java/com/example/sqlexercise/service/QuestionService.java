package com.example.sqlexercise.service;

import com.example.sqlexercise.po.MainQuestion;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.vo.DraftVO;
import com.example.sqlexercise.vo.MainQuestionVO;
import com.example.sqlexercise.vo.ResponseVO;

import java.util.List;

public interface QuestionService {

    String getSchemaNameByMainId(int mainId);

    String getSchemaConstructorByMainId(int mainId);

    List<SubQuestion> getSubQuestionByMainId(int mainId);

    SubQuestion getSubQuestionBySubId(int subId);

    MainQuestion getMainQuestionByMainId(int mainId);

    List<MainQuestionVO> getMainQuestionsByPage(int page, int pageSize);

    ResponseVO saveDraft(DraftVO draftVO);

    ResponseVO getDraft(String userId, Integer mainId, Integer subId);
}
