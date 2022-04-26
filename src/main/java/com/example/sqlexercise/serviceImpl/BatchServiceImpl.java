package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.BatchMapper;
import com.example.sqlexercise.data.PassRecordMapper;
import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.po.Batch;
import com.example.sqlexercise.po.PassRecord;
import com.example.sqlexercise.service.BatchService;
import com.example.sqlexercise.vo.BatchVO;
import com.example.sqlexercise.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BatchServiceImpl implements BatchService {

    private final SqlDatabaseServiceImpl sqlDatabaseService;
    private final BatchMapper batchMapper;
    private final boolean useMessageQueue;
    private final PassRecordMapper passRecordMapper;

    @Autowired
    public BatchServiceImpl(SqlDatabaseServiceImpl sqlDatabaseService, BatchMapper batchMapper, PassRecordMapper passRecordMapper) {
        this.sqlDatabaseService = sqlDatabaseService;
        this.batchMapper = batchMapper;
        this.passRecordMapper = passRecordMapper;
        this.useMessageQueue = false;
    }

    @Override
    public ResponseVO createBatch(BatchVO batchVO) {
        if(useMessageQueue){
            return createBatchAsync(batchVO);
        }else{
            return createBatchSync(batchVO);
        }
    }

    private ResponseVO createBatchAsync(BatchVO batchVO) {
        //TODO Spring + activeMQ
        return null;
    }


    private ResponseVO createBatchSync(BatchVO batchVO) {
        String driver = batchVO.getDriver();
        Batch batch = new Batch();
        BeanUtils.copyProperties(batchVO, batch);
        Date now = new Date();
        batch.setCreated_at(now);
        batch.setUpdated_at(now);
        batch.setId(UUID.randomUUID().toString());
        batchMapper.create(batch);
        executeBatch(batch ,batch.getMain_id(),batch.getSub_id(),driver,batchVO.getBatch_text());
        return ResponseVO.success("batch is running! batchID is "+batch.getId());
    }

    @Override
    public Batch getBatch(String batchId) {
        return batchMapper.getById(batchId);
    }

    private void executeBatch(Batch batch, int main_id, int sub_id, String driver, String sqlText){
        ResultOfTask answer = sqlDatabaseService.getStandardAnswer(sub_id, driver);
        Map options = new HashMap();
        options.put("skipPre", true);
        ResultOfTask queryResult = (ResultOfTask) sqlDatabaseService.runSqlTask(main_id, driver, sqlText, options);
        if(isPass(queryResult, answer)){
            PassRecord passRecord = new PassRecord();
            passRecord.setBatchId(batch.getId());
            passRecord.setMainId(main_id);
            passRecord.setSubId(sub_id);
            passRecord.setUserId(batch.getUser_id());
            passRecord.setCreatedAt(new Date());
            passRecord.setUpdatedAt(new Date());
            passRecord.setPoint(100);
            passRecordMapper.create(passRecord);
            System.out.println(batch.getId()+" passed!");
        }else{
            System.out.println(batch.getId()+" didn't pass!");
        }
    }

    //针对标准结果集和sql运行得到的结果集进行打分
    private boolean isPass(ResultOfTask queryResult, ResultOfTask standard){
        //首先确保运行不出错
        if(queryResult.error!=null){
            return false;
        }
        //先比较行数是否一致
        if(queryResult.sheet.size()!=standard.sheet.size()){
            return false;
        }
        //再比较列数是否一致
        if(queryResult.sheet.get(0).size()!=standard.sheet.get(0).size()){
            return false;
        }
        //逐行比较每一列
        for (int i = 0; i < queryResult.sheet.size(); i++) {
            if (!compareRow(queryResult.sheet.get(i), standard.sheet.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean compareRow(ArrayList<String> row, ArrayList<String> standard){
        for(int k = 0; k<row.size(); k++){
            if(!row.get(k).equals(standard.get(k))){
                return false;
            }
        }
        return true;
    }


}
