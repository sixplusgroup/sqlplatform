package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.po.Batch;
import com.example.sqlexercise.service.AsyncService;
import com.example.sqlexercise.service.SqlDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class BatchAsyncService implements AsyncService {

    private final SqlDatabaseService sqlDatabaseService;

    @Autowired
    public BatchAsyncService(SqlDatabaseService sqlDatabaseService) {
        this.sqlDatabaseService = sqlDatabaseService;
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<ResultOfTask> getStandardAnswer(Batch batch, String driver) {
        ResultOfTask answer = sqlDatabaseService.getStandardAnswer(batch.getSubId(), driver);
        return CompletableFuture.completedFuture(answer);
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<ResultOfTask> getUserAnswer(Batch batch, String driver, String sqlText, Map<String, Object> options) {
        ResultOfTask answer = (ResultOfTask) sqlDatabaseService.runMysqlTask(batch.getMainId(), driver, sqlText, options);
        return CompletableFuture.completedFuture(answer);
    }
}
