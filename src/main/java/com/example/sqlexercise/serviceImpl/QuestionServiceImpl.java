package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.*;
import com.example.sqlexercise.lib.Constants;
import com.example.sqlexercise.po.Draft;
import com.example.sqlexercise.po.MainQuestion;
import com.example.sqlexercise.po.QuestionState;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.service.QuestionService;
import com.example.sqlexercise.vo.DraftVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j(topic = "com.example.sqlexercise.serviceImpl.QuestionServiceImpl")
@Service
public class QuestionServiceImpl implements QuestionService {

    private SubQuestionMapper subQuestionMapper;
    private MainQuestionMapper mainQuestionMapper;
    private QuestionStateMapper questionStateMapper;
    private DraftMapper draftMapper;
    private BatchMapper batchMapper;

    @Autowired
    public QuestionServiceImpl(SubQuestionMapper subQuestionMapper,
                               MainQuestionMapper mainQuestionMapper,
                               QuestionStateMapper questionStateMapper, DraftMapper draftMapper, BatchMapper batchMapper) {
        this.subQuestionMapper = subQuestionMapper;
        this.mainQuestionMapper = mainQuestionMapper;
        this.questionStateMapper = questionStateMapper;
        this.draftMapper = draftMapper;
        this.batchMapper = batchMapper;
    }

    @Override
    public String getSchemaNameByMainId(int mainId) {
        if (mainQuestionMapper.selectById(mainId) == null) {
            log.info("Info of main question " + mainId + " is not found!");
            return "该mainId不存在";
        }
        return "main_" + mainId;
    }

    @Override
    public String getSchemaConstructorByMainId(int mainId) {
        MainQuestion mainQuestion = mainQuestionMapper.selectById(mainId);
        if (mainQuestion == null) {
            log.info("Info of main question " + mainId + " is not found!");
        }
        try {
            Path dbPath = Paths.get("src/main/resources/" + mainQuestion.getDbPath());
            if (!Files.exists(dbPath)) {
                log.info("File of main question" + mainId + "is not found!");
            }
            return Files.readString(dbPath);
        } catch (Exception e) {
            e.printStackTrace();
            return "IOException occurred!";
        }
    }

    @Override
    public List<SubQuestion> getSubQuestionByMainId(int mainId) {
        return subQuestionMapper.selectByMainId(mainId);
    }

    @Override
    public SubQuestion getSubQuestionBySubId(int subId) {
        return subQuestionMapper.selectBySubId(subId);
    }

    @Override
    public MainQuestion getMainQuestionByMainId(int mainId) {
        return mainQuestionMapper.selectById(mainId);
    }

    @Override
    public Object getMainQuestionsByPage(String userId, int pageSize, int page) {
        int from = (page - 1) * pageSize;
        List<Map<String, Object>> res = new ArrayList<>();
        // 获取大题总数
        Integer total = mainQuestionMapper.countTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("totalMainQuestionNum", total);
        res.add(map);
        // 查一页大题信息
        List<Map<String, Object>> questions = mainQuestionMapper.selectByPage(userId, from, pageSize);
        res.addAll(questions);
        return res;
    }

    /**
     * 保存用户做题草稿
     */
    @Override
    public String saveDraft(DraftVO draftVO) {
        Draft draft = draftMapper.select(draftVO.getUserId(), draftVO.getMainId(), draftVO.getSubId());
        boolean firstSave = draft == null;
        draft = new Draft();
        org.springframework.beans.BeanUtils.copyProperties(draftVO, draft);
        draft.setSaveTime(new Date());
        if (firstSave) {
            draftMapper.insert(draft);
        } else {
            draftMapper.update(draft);
        }
        return Constants.Message.SAVE_DRAFT_SUCCEED;
    }

    /**
     * 获取用户保存的做题草稿
     */
    @Override
    public DraftVO getDraft(String userId, Integer mainId, Integer subId) {
        Draft draft = draftMapper.select(userId, mainId, subId);
        if (draft == null) {
            return null;
        }
        DraftVO draftVO = new DraftVO();
        org.springframework.beans.BeanUtils.copyProperties(draft, draftVO);

        return draftVO;
    }

    /**
     * 修改一个subQuestion的状态为 已收藏
     */
    @Override
    public String star(String userId, Integer mainId, Integer subId) {
        // 查询数据库中是否已经有该题目针对该用户的状态记录
        QuestionState questionState = questionStateMapper.select(userId, mainId, subId);
        if (questionState == null) {
            // 若没有，则insert
            questionState = new QuestionState(userId, mainId, subId, true, Constants.QuestionState.NOT_STARTED);
            questionStateMapper.insert(questionState);
        } else {
            // 若有，则update，is_starred字段更新为1，即已收藏
            questionStateMapper.updateIsStarred(userId, mainId, subId, true);
        }
        return Constants.Message.STAR_SUCCEED;
    }

    /**
     * 修改一个subQuestion的状态为 未收藏
     */
    @Override
    public String unStar(String userId, Integer mainId, Integer subId) {
        questionStateMapper.updateIsStarred(userId, mainId, subId, false);
        return Constants.Message.UNSTAR_SUCCEED;
    }

    /**
     * 获取某用户对某一个subQuestion是否收藏、是否通过
     */
    @Override
    public Object getIsStarredAndStateOf(String userId, Integer mainId, Integer subId) {
        Map<String, Object> res = questionStateMapper.selectIsStarredAndStateOf(userId, mainId, subId);
        if (res == null) {
            res = new HashMap<>();
            res.put("isStarred", "未收藏");
            res.put("state", "未开始");
        }
        return res;
    }

    /**
     * 查询某用户对于某subQuestion的提交记录，包含通过和未通过的，且返回的记录列表按时间先后排序，越近的提交记录越靠前
     */
    @Override
    public List<Object> getSubmitRecord(String userId, Integer mainId, Integer subId) {
        List<Object> submitRecord = batchMapper.selectSubmitRecord(userId, mainId, subId);
        return submitRecord;
    }

}
