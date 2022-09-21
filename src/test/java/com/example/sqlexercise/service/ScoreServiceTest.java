package com.example.sqlexercise.service;

import com.example.sqlexercise.data.AnswerSetMapper;
import com.example.sqlexercise.po.AnswerSet;
import com.example.sqlexercise.vo.GetScoreVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author shenyichen
 * @date 2022/9/20
 **/
@SpringBootTest
public class ScoreServiceTest {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private AnswerSetMapper answerSetMapper;

    @Test
    public void testGetScore() {
        GetScoreVO getScoreVO = new GetScoreVO(176,882,"select max(Salary) SecondHighestSalary\n" +
                "from employee\n" +
                "where\n" +
                "salary<(select max(salary) from employee)",100f);
        System.out.println(scoreService.getScore(getScoreVO));
    }

    @Test
    public void testAdd2AnswerSet() {
        AnswerSet stuAnswer = new AnswerSet();
        stuAnswer.setMainId(176);
        stuAnswer.setSubId(882);
//        stuAnswer.setAnswer("select max(Salary) SecondHighestSalary\n" +
//                "from employee\n" +
//                "where\n" +
//                "salary<(select max(salary) from employee)");
        stuAnswer.setAnswer("select max(Salary) SecondHighestSalary\n" +
                "from employee\n" +
                "where\n");
        AnswerSet ans = answerSetMapper.getByAnswer(stuAnswer);
        if (ans == null) {
            stuAnswer.setCreatedAt(new Date());
            stuAnswer.setUpdatedAt(new Date());
            answerSetMapper.create(stuAnswer);
            System.out.println("not exist");
        }
        else if (ans.getState() == 1) {
            stuAnswer.setUpdatedAt(new Date());
            answerSetMapper.updateStatus(stuAnswer);
            System.out.println("invalid");
        }
        else {
            System.out.println("already exist");
        }
    }
}
