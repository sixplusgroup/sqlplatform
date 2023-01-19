package com.example.sqlexercise.service;

import com.example.sqlexercise.po.Batch;
import com.example.sqlexercise.vo.BatchVO;
import com.example.sqlexercise.vo.ResponseVO;

public interface BatchService {

    String processBatch(BatchVO batchVO, String processSqlMode);

    Batch getBatch(String batchId);

    public String testMQ() throws InterruptedException;
}
