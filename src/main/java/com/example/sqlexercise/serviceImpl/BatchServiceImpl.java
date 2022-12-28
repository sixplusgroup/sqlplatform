package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.AnswerSetMapper;
import com.example.sqlexercise.data.BatchMapper;
import com.example.sqlexercise.data.PassRecordMapper;
import com.example.sqlexercise.data.QuestionStateMapper;
import com.example.sqlexercise.lib.Constants;
import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.po.Batch;
import com.example.sqlexercise.po.PassRecord;
import com.example.sqlexercise.po.QuestionState;
import com.example.sqlexercise.service.BatchService;
import com.example.sqlexercise.service.ScoreService;
import com.example.sqlexercise.vo.BatchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j(topic = "com.example.sqlexercise.serviceImpl.BatchServiceImpl")
@Service
public class BatchServiceImpl implements BatchService {

    private final SqlDatabaseServiceImpl sqlDatabaseService;
    private final boolean useMessageQueue;

    private final ScoreService scoreService;
    private final AnswerSetMapper answerSetMapper;
    private PassRecordMapper passRecordMapper;
    private BatchMapper batchMapper;
    private QuestionStateMapper questionStateMapper;

    @Autowired
    public BatchServiceImpl(SqlDatabaseServiceImpl sqlDatabaseService, ScoreService scoreService,
                            AnswerSetMapper answerSetMapper, PassRecordMapper passRecordMapper,
                            BatchMapper batchMapper, QuestionStateMapper questionStateMapper) {
        this.sqlDatabaseService = sqlDatabaseService;
        this.passRecordMapper = passRecordMapper;
        this.batchMapper = batchMapper;
        this.scoreService = scoreService;
        this.answerSetMapper = answerSetMapper;
        this.questionStateMapper = questionStateMapper;
        this.useMessageQueue = false;
    }

    @Override
    public String processBatch(BatchVO batchVO, String processSqlMode) {
        if (useMessageQueue) {
            return processBatchAsync(batchVO, processSqlMode);
        } else {
            return processBatchSync(batchVO, processSqlMode);
        }
    }

    private String processBatchAsync(BatchVO batchVO, String processSqlMode) {
        //TODO Spring + MQ 消息中间件选型待定
        return null;
    }

    private String processBatchSync(BatchVO batchVO, String processSqlMode) {
        String driver = batchVO.getDriver();
        Batch batch = new Batch();
        BeanUtils.copyProperties(batchVO, batch);
        Date now = new Date();
        batch.setCreatedAt(now);
        batch.setUpdatedAt(now);
        batch.setId(UUID.randomUUID().toString());
        // 只有submit时，才将该条batch入库
        if (processSqlMode.equals(Constants.ProcessSqlMode.SUBMIT)) {
            batchMapper.insert(batch);
        }
        // 执行
        log.info("batch " + batch.getId() + " is running. ");
        String res = executeBatch(batch, batch.getMainId(), batch.getSubId(), driver, batchVO.getBatchText(), processSqlMode);
        return res;
    }

    @Override
    public Batch getBatch(String batchId) {
        return batchMapper.selectById(batchId);
    }

    private String executeBatch(Batch batch, int main_id, int sub_id, String driver, String sqlText, String processSqlMode) {
        // 现在的判分是先运行一次标准答案，然后与结果比对
        // TODO 引入redis对频繁使用的标准结果集进行多级缓存
        ResultOfTask answer = sqlDatabaseService.getStandardAnswer(sub_id, driver);
        Map<String, Object> options = new HashMap<>();
        options.put("skipPre", false);
        ResultOfTask queryResult = (ResultOfTask) sqlDatabaseService.runSqlTask(main_id, driver, sqlText, options);

        if (isPass(queryResult, answer)) {
            // 如果通过且是submit就将记录入库，并修改该题目对于该用户的相关状态
            if (processSqlMode.equals(Constants.ProcessSqlMode.SUBMIT)) {
                PassRecord passRecord = new PassRecord();
                passRecord.setBatchId(batch.getId());
                passRecord.setMainId(main_id);
                passRecord.setSubId(sub_id);
                passRecord.setUserId(batch.getUserId());
                passRecord.setCreatedAt(new Date());
                passRecord.setUpdatedAt(new Date());
                passRecord.setPoint(100);
                passRecordMapper.insert(passRecord);
                updateQuestionState(batch.getUserId(), batch.getMainId(), batch.getSubId(), Constants.QuestionState.PASSED);
            }
            // 如果是run则只返回结果
            log.info("batch " + batch.getId() + " passed. " + new Throwable().getStackTrace()[0]);
            // 如果静态分析得到的评分非满分，需要将其加入答案集
//            GetScoreVO getScoreVO = new GetScoreVO(main_id, sub_id, sqlText, 100f);
//            float score = scoreService.getScore(getScoreVO);
//            if (score < 100f) {
//                AnswerSet stuAnswer = new AnswerSet();
//                stuAnswer.setMainId(main_id);
//                stuAnswer.setSubId(sub_id);
//                stuAnswer.setAnswer(sqlText);
//                AnswerSet ans = answerSetMapper.getByAnswer(stuAnswer);
//                if (ans == null) {
//                    stuAnswer.setCreatedAt(new Date());
//                    stuAnswer.setUpdatedAt(new Date());
//                    answerSetMapper.create(stuAnswer);
//                } else if (ans.getState() == 1) {
//                    stuAnswer.setUpdatedAt(new Date());
//                    answerSetMapper.updateStatus(stuAnswer);
//                }
//            }
            return "Passed";
        } else {
            updateQuestionState(batch.getUserId(), batch.getMainId(), batch.getSubId(), Constants.QuestionState.SUBMITTED_BUT_NOT_PASS);
            log.info("batch " + batch.getId() + " didn't pass. " + new Throwable().getStackTrace()[0]);
            return "Didn't pass";
        }
    }

    private void updateQuestionState(String userId, int mainId, int subId, int toState) {
        QuestionState questionState = questionStateMapper.select(userId, mainId, subId);
        if (questionState == null) {
            questionState = new QuestionState(userId, mainId, subId, false, toState);
            questionStateMapper.insert(questionState);
        } else {
            // 未开始-0 < 提交未通过-1 < 已通过-2
            // questionState只会单向变化
            if (questionState.getState() < toState) {
                questionStateMapper.updateState(userId, mainId, subId, toState);
            }
        }
    }

    //针对标准结果集和sql运行得到的结果集进行打分
    private boolean isPass(ResultOfTask queryResult, ResultOfTask standard) {
        //首先确保运行不出错
        if (queryResult.error != null) {
            return false;
        }
        //先比较行数是否一致
        if (queryResult.sheet.size() != standard.sheet.size()) {
            return false;
        }
        if (queryResult.sheet.size() != 0) {
            //再比较列数是否一致
            if (queryResult.sheet.get(0).size() != standard.sheet.get(0).size()) {
                return false;
            }
            //逐行比较每一列
            for (int i = 0; i < queryResult.sheet.size(); i++) {
                if (!compareRow(queryResult.sheet.get(i), standard.sheet.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean compareRow(ArrayList<String> row, ArrayList<String> standard) {
        for (int k = 0; k < row.size(); k++) {
            if (!row.get(k).equals(standard.get(k))) {
                return false;
            }
        }
        return true;
    }

}
