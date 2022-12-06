package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.DraftMapper;
import com.example.sqlexercise.data.MainQuestionMapper;
import com.example.sqlexercise.data.SubQuestionMapper;
import com.example.sqlexercise.po.Draft;
import com.example.sqlexercise.po.MainQuestion;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.service.QuestionService;
import com.example.sqlexercise.vo.DraftVO;
import com.example.sqlexercise.vo.MainQuestionVO;
import com.example.sqlexercise.vo.ResponseVO;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private SubQuestionMapper subQuestionMapper;
    private MainQuestionMapper mainQuestionMapper;
    private DraftMapper draftMapper;

    @Autowired
    public QuestionServiceImpl(SubQuestionMapper subQuestionMapper,
                               MainQuestionMapper mainQuestionMapper,
                               DraftMapper draftMapper) {
        this.subQuestionMapper = subQuestionMapper;
        this.mainQuestionMapper = mainQuestionMapper;
        this.draftMapper = draftMapper;
    }

    @Override
    public String getSchemaNameByMainId(int mainId) {
        if (mainQuestionMapper.selectById(mainId) == null) {
            System.out.println("Info of main question " + mainId + " is not found!");
            return "error!";
        }
        return "main_" + mainId;
    }

    @Override
    public String getSchemaConstructorByMainId(int mainId) {
        MainQuestion mainQuestion = mainQuestionMapper.selectById(mainId);
        if (mainQuestion == null) {
            System.out.println("Info of main question " + mainId + " is not found!");
        }
        try {
            Path dbPath = Paths.get("src/main/resources/" + mainQuestion.getDbPath());
            if (!Files.exists(dbPath)) {
                System.out.println("File of main question" + mainId + "is not found!");
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
    public List<MainQuestionVO> getMainQuestionsByPage(int page, int pageSize) {
        int from = (page - 1) * pageSize;
        List<Map<String, Object>> maps = mainQuestionMapper.selectByPage(from, pageSize);
        // 将PO转换为VO返回
        return maps.stream().map(map -> {
            MainQuestionVO mainQuestionVO = new MainQuestionVO();
            try {
                BeanUtils.populate(mainQuestionVO, map);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return mainQuestionVO;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseVO saveDraft(DraftVO draftVO) {
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
        return ResponseVO.success("保存成功！");
    }

    @Override
    public ResponseVO getDraft(String userId, Integer mainId, Integer subId) {
        Draft draft = draftMapper.select(userId, mainId, subId);
        if (draft == null) {
            return ResponseVO.failure("未保存过草稿！");
        }
        DraftVO draftVO = new DraftVO();
        org.springframework.beans.BeanUtils.copyProperties(draft, draftVO);

        return ResponseVO.success(draftVO);
    }

}
