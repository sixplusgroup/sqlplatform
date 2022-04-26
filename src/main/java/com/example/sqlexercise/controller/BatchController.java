package com.example.sqlexercise.controller;

import com.example.sqlexercise.service.BatchService;
import com.example.sqlexercise.vo.BatchVO;
import com.example.sqlexercise.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//处理sql提交请求
//查询sql运行结果
@RestController
public class BatchController {

    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/api/batch/create")
    public ResponseVO createBatch(@RequestBody BatchVO batchVO){
        return batchService.createBatch(batchVO);
    }

    @GetMapping("/api/batch/get")
    public ResponseVO getBatch(@RequestParam String batchId){
        return ResponseVO.success(batchService.getBatch(batchId));
    }
}
