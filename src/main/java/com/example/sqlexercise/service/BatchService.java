package com.example.sqlexercise.service;

import com.example.sqlexercise.po.Batch;
import com.example.sqlexercise.vo.BatchVO;
import com.example.sqlexercise.vo.ResponseVO;

public interface BatchService {

    ResponseVO createBatch(BatchVO batchVO);

    Batch getBatch(String batchId);

}
