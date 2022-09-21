package com.example.sqlexercise.service;

import com.example.sqlexercise.vo.GetScoreVO;

/**
 * @author shenyichen
 * @date 2022/9/20
 **/
public interface ScoreService {
    float getScore(GetScoreVO getScoreVO);
}
