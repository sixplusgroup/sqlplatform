package org.example;

import org.example.PartialMarking;
import org.example.util.Constants;
import org.example.util.data.GetQuestionInfo;
import org.example.util.data.OperateAnswerSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shenyichen
 * @date 2022/3/28
 **/
@RestController
@RequestMapping("/api/score")
public class GradingController {

    @GetMapping("/getScore")
    public float getScore(Integer mainId, Integer subId, String studentSql, float maxScore) {
        return 100.0f;
//        return getScore(mainId, subId, studentSql, maxScore, Constants.dbType);
    }

    public float getScore(Integer mainId, Integer subId, String studentSql, float maxScore, String dbType) {
        List<String> answers = OperateAnswerSet.getAnswers(mainId, subId);
        List<String> envSqls = GetQuestionInfo.getEnvSqls(mainId);
        PartialMarking partialMarking = new PartialMarking(dbType, envSqls);
        return partialMarking.partialMarkForAnswerSet(answers, studentSql, maxScore);
    }

    @GetMapping("/addToAnswerSet")
    public void addToAnswerSet(Integer mainId, Integer subId, String instrSql) {
        addToAnswerSet(mainId, subId, instrSql, Constants.dbType);
    }

    public void addToAnswerSet(Integer mainId, Integer subId, String instrSql, String dbType) {
        float score = getScore(mainId, subId, instrSql, 100.0f, dbType);
        if (score < 100.0f) {
            OperateAnswerSet.addAnswer(mainId, subId, instrSql);
        }
    }
}
