package com.example.sqlexercise.service;

import com.example.sqlexercise.data.SubQuestionMapper;
import com.example.sqlexercise.lib.Constants;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.vo.BatchVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BatchServiceTest {

    private final BatchService batchService;
    private final SubQuestionMapper subQuestionMapper;

    @Autowired
    BatchServiceTest(BatchService batchService, SubQuestionMapper subQuestionMapper){
        this.batchService = batchService;
        this.subQuestionMapper = subQuestionMapper;
    }

    @Test
    void processBatch() throws InterruptedException {
        Thread.sleep(25 * 1000);
        List<SubQuestion> subQuestions = subQuestionMapper.selectAll();
        for (SubQuestion subQuestion : subQuestions) {
            BatchVO batchVO = new BatchVO();
            batchVO.setBatchText(subQuestion.getAnswer());
            batchVO.setMainId(subQuestion.getMainId());
            batchVO.setSubId(subQuestion.getId());
            batchVO.setDriver("mysql");
            batchVO.setUserId("38748ac0-1f40-4ada-b5b7-ee4df2e20128");
            String s = batchService.processBatch(batchVO, Constants.ProcessSqlMode.RUN);
            Assertions.assertEquals("Passed", s);
        }
    }

    @Test
    public void test1(){
        BatchVO batchVO = new BatchVO();
        batchVO.setBatchText("select w.eno \n" +
                "from works w\n" +
                "group by w.eno\n" +
                "having sum(hours) > 1000;");
        batchVO.setDriver("mysql");
        batchVO.setMainId(1);
        batchVO.setUserId("ba01ae2f-4baa-4b8f-9035-d6b9a87930a6");
        batchVO.setSubId(10);
        batchService.processBatch(batchVO, Constants.ProcessSqlMode.SUBMIT);
    }

    @Test
    public void test2(){
        BatchVO batchVO = new BatchVO();
        batchVO.setBatchText("select s.sname from sailors s\n" +
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
        batchVO.setMainId(2);
        batchVO.setUserId("ba01ae2f-4baa-4b8f-9035-d6b9a87930a6");
        batchVO.setSubId(14);
        batchService.processBatch(batchVO, Constants.ProcessSqlMode.SUBMIT);
    }
}
