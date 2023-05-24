package com.example.sqlexercise.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sqlexercise.config.RabbitMQConfig;
import com.example.sqlexercise.config.YmlProperties;
import com.example.sqlexercise.data.AnswerSetMapper;
import com.example.sqlexercise.data.BatchMapper;
import com.example.sqlexercise.data.PassRecordMapper;
import com.example.sqlexercise.data.QuestionStateMapper;
import com.example.sqlexercise.lib.Constants;
import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.po.AnswerSet;
import com.example.sqlexercise.po.Batch;
import com.example.sqlexercise.po.PassRecord;
import com.example.sqlexercise.po.QuestionState;
import com.example.sqlexercise.service.BatchService;
import com.example.sqlexercise.service.QuestionService;
import com.example.sqlexercise.service.ScoreService;
import com.example.sqlexercise.service.SqlDatabaseService;
import com.example.sqlexercise.vo.BatchVO;
import com.example.sqlexercise.vo.DraftVO;
import com.example.sqlexercise.vo.GetScoreVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j(topic = "com.example.sqlexercise.serviceImpl.BatchServiceImpl")
@Service
public class BatchServiceImpl implements BatchService {

    private final boolean useMessageQueue;
    private final RabbitTemplate rabbitTemplate;

    private final SqlDatabaseService sqlDatabaseService;
    private final ScoreService scoreService;
    private final QuestionService questionService;

    private final AnswerSetMapper answerSetMapper;
    private final PassRecordMapper passRecordMapper;
    private final BatchMapper batchMapper;
    private final QuestionStateMapper questionStateMapper;

    private final YmlProperties ymlProperties;

    @Autowired
    public BatchServiceImpl(SqlDatabaseService sqlDatabaseService, ScoreService scoreService,
                            AnswerSetMapper answerSetMapper, PassRecordMapper passRecordMapper,
                            BatchMapper batchMapper, QuestionStateMapper questionStateMapper,
                            QuestionService questionService, RabbitTemplate rabbitTemplate, YmlProperties ymlProperties) {
        this.sqlDatabaseService = sqlDatabaseService;
        this.passRecordMapper = passRecordMapper;
        this.batchMapper = batchMapper;
        this.scoreService = scoreService;
        this.answerSetMapper = answerSetMapper;
        this.questionStateMapper = questionStateMapper;
        this.questionService = questionService;
        this.ymlProperties = ymlProperties;
        this.useMessageQueue = false;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setReplyTimeout(10000); // 超时时间10s
    }

    @Override
    public Object processBatch(BatchVO batchVO, String processSqlMode) {
        if (useMessageQueue) {
            return processBatchAsync(batchVO, processSqlMode);
        } else {
            return processBatchSync(batchVO, processSqlMode);
        }
    }

//    @Override
//    public String testMQ() throws InterruptedException {
//        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
//        Map<String, Object> message = new HashMap<>(2);
//        message.put("id", "hello");
//        String jsonString = JSON.toJSONString(message);
//        String result = (String) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.DirectExchange,
//                RabbitMQConfig.DirectRouting, jsonString, correlationId);
////        Thread.sleep(6000);
////        while(result == null) {
////            try {
////                System.out.println("waiting for result...");
////                Thread.sleep(1000);
////            } catch (Exception e) {
////                log.error("error when running batch " + message.get("id") + " using rabbitmq. ");
////                return "Didn't pass";
////            }
////        }
//        return result;
//    }
//
//    @RabbitListener(queues = {RabbitMQConfig.DirectQueue})
//    @RabbitHandler
//    public String testConsumer(Message message) throws InterruptedException {
//        Thread.sleep(6000);
//        String byteMessage = new String(message.getBody(), StandardCharsets.UTF_8);
//        JSONObject messageObject = JSON.parseObject(byteMessage);
//        String id = messageObject.getString("id");
//        if (StringUtils.isEmpty(id)) {
//            log.error("error when consume message " + id + " using rabbitmq. ");
//            return "Didn't pass";
//        }
//        return id;
//    }

    /**
     * 使用 RabbitMQ 作为消息队列
     */
    private String processBatchAsync(BatchVO batchVO, String processSqlMode) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        Map<String, Object> message = new HashMap<>(2);
        message.put("batchVO", batchVO);
        message.put("processSqlMode", processSqlMode);
        message.put("id", UUID.randomUUID().toString());
        String jsonString = JSON.toJSONString(message);
        String result = "Didn't pass";
        result = (String) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.DirectExchange,
                RabbitMQConfig.DirectRouting, jsonString, correlationId);
//        while(result == null) {
//            try {
//                Thread.sleep(10000);
//            } catch (Exception e) {
//                log.error("error when running batch " + message.get("id") + " using rabbitmq. ");
//                return "Didn't pass";
//            }
//        }
        return result;
    }

    @RabbitListener(queues = {RabbitMQConfig.DirectQueue})
    public Object batchMessageConsumer(Message message) {
        String byteMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        JSONObject messageObject = JSON.parseObject(byteMessage);
        BatchVO batchVO = JSON.parseObject(messageObject.getString("batchVO"), BatchVO.class);
        String processSqlMode = messageObject.getString("processSqlMode");
        String id = messageObject.getString("id");
        if (StringUtils.isEmpty(processSqlMode) || StringUtils.isEmpty(id)) {
            log.error("error when consume message " + id + " using rabbitmq. ");
            return "Didn't pass";
        }
        return processBatchSync(batchVO, processSqlMode, id);
    }

    private Object processBatchSync(BatchVO batchVO, String processSqlMode, String id) {
        String driver = batchVO.getDriver();
        Batch batch = new Batch();
        BeanUtils.copyProperties(batchVO, batch);
        Date now = new Date();
        batch.setCreatedAt(now);
        batch.setUpdatedAt(now);
        if (id == null) {
            batch.setId(UUID.randomUUID().toString());
        } else {
            batch.setId(id);
        }
        // 只有submit时，才将该条batch写入数据库，并保存草稿
        if (processSqlMode.equals(Constants.ProcessSqlMode.SUBMIT)) {
            // batch写入数据库
            batchMapper.insert(batch);
            // 保存草稿，写入数据库
            DraftVO draftVO = new DraftVO(
                    batchVO.getUserId(),
                    batchVO.getMainId(),
                    batchVO.getSubId(),
                    batchVO.getBatchText(),
                    now
            );
            questionService.saveDraft(draftVO);
        }
        log.info("batch " + batch.getId() + " is running.");

        // 执行SQL前，用空格替换用户提交的SQL中的\n和\t
        String batchText = batchVO.getBatchText().replaceAll("\n", " ").replaceAll("\t", " ");

        Object res = null;
        // 执行
        if (driver.equals("mysql")) {
            res = executeBatchForMysql(batch, driver, batchText, processSqlMode);
        } else if (driver.equals("oceanbase")) {
            res = executeBatchForOceanbase(batch, batchText);
        }

        return res;
    }

    private Object processBatchSync(BatchVO batchVO, String processSqlMode) {
        return processBatchSync(batchVO, processSqlMode, null);
    }

    @Override
    public Batch getBatch(String batchId) {
        return batchMapper.selectById(batchId);
    }

    /**
     * 该方法目前只执行用户在MySQL题目中提交的代码
     *
     * @param sqlText 用户提交的select语句
     */
    private String executeBatchForMysql(Batch batch, String driver, String sqlText, String processSqlMode) {
        // 获取本题标准结果集
        ResultOfTask answer = sqlDatabaseService.getStandardAnswer(batch.getSubId(), driver);
        Map<String, Object> options = new HashMap<>();
        // 设置 执行preTask方法，忽略postTask方法
        options.put("skipPre", false);
        options.put("skipPost", true);
        ResultOfTask queryResult = (ResultOfTask) sqlDatabaseService.runMysqlTask(batch.getMainId(), driver, sqlText, options);

        if (isPass(queryResult, answer)) {
            // 只有submit时，才将记录入库，并修改该题目对于该用户的相关状态
            if (processSqlMode.equals(Constants.ProcessSqlMode.SUBMIT)) {
                PassRecord passRecord = new PassRecord(
                        batch.getUserId(),
                        batch.getMainId(),
                        batch.getSubId(),
                        batch.getId(),
                        100,
                        new Date(),
                        new Date()
                );
                passRecordMapper.insert(passRecord);
                updateQuestionState(batch.getUserId(), batch.getMainId(), batch.getSubId(), Constants.QuestionState.PASSED);
            }
            log.info("batch " + batch.getId() + " passed.");
            // 如果静态分析得到的评分非满分，需要将其加入答案集
            GetScoreVO getScoreVO = new GetScoreVO(batch.getMainId(), batch.getSubId(), sqlText, 100f);
            float score = scoreService.getScore(getScoreVO);
            if (score < 100f) {
                AnswerSet stuAnswer = new AnswerSet();
                stuAnswer.setMainId(batch.getMainId());
                stuAnswer.setSubId(batch.getSubId());
                stuAnswer.setAnswer(sqlText);
                AnswerSet ans = answerSetMapper.getByAnswer(stuAnswer);
                if (ans == null) {
                    stuAnswer.setCreatedAt(new Date());
                    stuAnswer.setUpdatedAt(new Date());
                    answerSetMapper.create(stuAnswer);
                } else if (ans.getState() == 1) {
                    stuAnswer.setUpdatedAt(new Date());
                    answerSetMapper.updateStatus(stuAnswer);
                }
            }
            return Constants.Message.PASSED;
        } else {
            // 如果是提交，才会修改题目状态；运行则不会
            if (processSqlMode.equals(Constants.ProcessSqlMode.SUBMIT)) {
                // 修改题目状态
                updateQuestionState(batch.getUserId(), batch.getMainId(), batch.getSubId(), Constants.QuestionState.SUBMITTED_BUT_NOT_PASS);
            }
            log.info("batch " + batch.getId() + " didn't pass.");
            return Constants.Message.NOT_PASSED;
        }
    }

    /**
     * 目前系统中涉及到OceanBase的题目只有一道索引相关的。因为该题目重点在于体验索引对于查询性能的提升效果，
     * 且数据量较大，故此处代码实现逻辑有所不同，单独写成一个方法
     *
     * @param sqlText 用户提交的create index语句
     * @return String，包含
     *              无索引查询耗时 queryTimeWithoutIndex，
     *              创建索引耗时 createIndexTime，
     *              创建索引后的查询耗时 queryTimeAfterIndexCreation
     *         约定格式为 xxx:123,xxx:123,xxx:123
     */
    private Map<String, String> executeBatchForOceanbase(Batch batch, String sqlText) {
        // 根据用户id的hashCode取模，得到该用户操作的表的编号
        int targetTableNum = Math.abs(batch.getUserId().hashCode() % ymlProperties.getOceanbaseTableDuplicateNum());
        // 替换创建索引的SQL语句中的表名
        String createIndexSql = sqlText.replaceAll("app_user", "app_user_" + targetTableNum);
        String querySql = questionService.getSubQuestionBySubId(batch.getSubId()).getAnswer()
                .replaceAll("app_user", "app_user_" + targetTableNum);
        // 执行任务
        Map<String, String> times = sqlDatabaseService.runOceanbaseTask(createIndexSql, querySql, targetTableNum);
        if (times == null) {
            // 执行失败
            return null;
        }
        return times;
//        StringBuilder result = new StringBuilder();
//        for (String item : times.keySet()) {
//            result.append(item).append(":").append(times.get(item)).append(",");
//        }
//        result.deleteCharAt(result.length() - 1);
//        return result.toString();
    }

    /**
     * 更新题目状态
     *
     * @param toState 将该题对于该用户的状态设为toState
     */
    private void updateQuestionState(String userId, int mainId, int subId, int toState) {
        QuestionState questionState = questionStateMapper.select(userId, mainId, subId);
        if (questionState == null) {
            questionState = new QuestionState(userId, mainId, subId, false, toState);
            questionStateMapper.insert(questionState);
        } else {
            // questionState只会单向变化: 未开始-0 => 提交未通过-1 => 已通过-2
            if (questionState.getState() < toState) {
                questionStateMapper.updateState(userId, mainId, subId, toState);
            }
        }
    }

    /**
     * 将sql运行得到的结果集 和 标准结果集进行比对，判分
     */
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
