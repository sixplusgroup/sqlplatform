package org.example;

import org.example.PartialMarking;
import org.example.util.Constants;
import org.example.util.data.GetQuestionInfo;
import org.example.util.data.OperateAnswerSet;
import org.example.util.vo.AddToAnswerSetVO;
import org.example.util.vo.GetScoreVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shenyichen
 * @date 2022/3/28
 **/
@RestController
@RequestMapping("/api/score")
public class GradingController {

    @PostMapping("/getScore")
    public float getScore(@RequestBody GetScoreVO data) {
        System.out.println("get score...");
        return getScore(data.getMainId(), data.getSubId(), data.getStudentSql(), data.getMaxScore(), Constants.dbType);
    }

    @GetMapping("/getCorrect")
    public List<String> getScore(Integer mainId, Integer subId) {
        return OperateAnswerSet.getAnswers(mainId, subId);
    }

    public float getScore(Integer mainId, Integer subId, String studentSql, float maxScore, String dbType) {
        List<String> answers = OperateAnswerSet.getAnswers(mainId, subId);
        List<String> envSqls = GetQuestionInfo.getEnvSqls(mainId);
        PartialMarking partialMarking = new PartialMarking(dbType, envSqls);
        return partialMarking.partialMarkForAnswerSet(answers, studentSql, maxScore);
    }

    @PostMapping("/addToAnswerSet")
    public void addToAnswerSet(@RequestBody AddToAnswerSetVO data) {
        System.out.println("add to answer set");
        addToAnswerSet(data.getMainId(), data.getSubId(), data.getInstrSql(), Constants.dbType);
    }

    public void addToAnswerSet(Integer mainId, Integer subId, String instrSql, String dbType) {
        float score = getScore(mainId, subId, instrSql, 100.0f, dbType);
        if (score < 100.0f) {
            OperateAnswerSet.addAnswer(mainId, subId, instrSql);
        }
    }

    public static void main(String[] args) {
        GetScoreVO data = new GetScoreVO();
        data.setMainId(1);
        data.setSubId(10);
        data.setStudentSql("select w.eno \n" +
                "from works w\n" +
                "group by w.eno\n" +
                "having sum(hours) > 100;");
        data.setMaxScore(100.0f);
        GradingController controller = new GradingController();
        controller.getScore(data);
    }
}
