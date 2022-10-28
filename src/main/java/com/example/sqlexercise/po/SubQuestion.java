package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class SubQuestion {
    int id;
    int mainId;
    String desc;
    String answer;
    boolean ordered;
    int difficulty;
    Date createdAt;
    Date updatedAt;
}
