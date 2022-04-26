package com.example.sqlexercise.service;

import com.example.sqlexercise.vo.BatchVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BatchServiceTest {

    private final BatchService batchService;

    @Autowired
    public BatchServiceTest(BatchService batchService){
        this.batchService = batchService;
    }

    @Test
    public void test(){
        BatchVO batchVO = new BatchVO();
        batchVO.setBatch_text("select w.eno \n" +
                "from works w\n" +
                "group by w.eno\n" +
                "having sum(hours) > 1000;");
        batchVO.setDriver("mysql");
        batchVO.setMain_id(1);
        batchVO.setUser_id("ba01ae2f-4baa-4b8f-9035-d6b9a87930a6");
        batchVO.setSub_id(10);
        batchService.createBatch(batchVO);
    }
}
