package com.example.sqlexercise.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sqlexercise.data.ExamMapper;
import com.example.sqlexercise.po.MainQPoint;
import com.example.sqlexercise.po.SubQPoint;
import com.example.sqlexercise.service.ScoreService;
import com.example.sqlexercise.vo.GetScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2022/9/20
 **/
@Service
public class ScoreServiceImpl implements ScoreService {

//    private final ExamMapper examMapper;
//
//    @Autowired
//    public ScoreServiceImpl(ExamMapper examMapper) {
//        this.examMapper = examMapper;
//    }

    @Override
    public float getScore(GetScoreVO getScoreVO) {
        try {
            String url="http://localhost:8000/api/score/getScore";
//            String url="http://172.29.4.28:8000/api/score/getScore";
            //设置请求头信息
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            //设置body部分
            HttpEntity<String> entity = new HttpEntity<String>(JSONObject.toJSONString(getScoreVO), headers);
            RestTemplate restTemplate=new RestTemplate();
            ResponseEntity<Float> result = restTemplate.exchange(url, HttpMethod.POST, entity, Float.class);
            System.out.println(result.getBody());
            return result.getBody();
        } catch (Exception e) {
            System.out.println("Exception in getScore: ");
            e.printStackTrace();
            return 0;
        }
    }

//    private Integer getSubQPoint(String examId, Integer mainId, Integer subId) {
//        try {
//            String questions = examMapper.getQuestionsById(examId);
//            List<MainQPoint> l = JSONObject.parseArray(questions, MainQPoint.class)
//                    .stream()
//                    .filter(q -> Integer.parseInt(q.getMainQuestion()) == mainId)
//                    .collect(Collectors.toList());
//            for (MainQPoint mainQPoint: l) {
//                List<SubQPoint> subQs = JSONObject.parseArray(mainQPoint.getSubQuestions(), SubQPoint.class)
//                        .stream()
//                        .filter(q -> q.getId().equals(subId))
//                        .collect(Collectors.toList());
//                if (subQs.size() > 0) {
//                    return subQs.get(0).getPoint();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Exception when getSubPoint in getScore: ");
//            e.printStackTrace();
//            return 0;
//        }
//        return 0;
//    }
}
