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
    public void test1(){
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

    @Test
    public void test2(){
        BatchVO batchVO = new BatchVO();
        batchVO.setBatch_text("select s.sname from sailors s\n" +
                "where s.age > 35 and not exists (\n" +
                "\tselect * \n" +
                "\tfrom reserves r join boats b\n" +
                "\ton r.bid = b.bid \n" +
                "\twhere  \n" +
                "\tr.sid = s.sid and \n" +
                "\tb.color = 'RED' and\n" +
                "\t(r.reserve_date between '2020-09-01' and '2020-09-30')\n" +
                ");");
        batchVO.setDriver("mysql");
        batchVO.setMain_id(2);
        batchVO.setUser_id("ba01ae2f-4baa-4b8f-9035-d6b9a87930a6");
        batchVO.setSub_id(14);
        batchService.createBatch(batchVO);
    }
}
