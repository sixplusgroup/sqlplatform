package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class SubQuestion {

    Integer id;

    int mainId;

    String description;

    String answer;

    boolean ordered;

    int difficulty;

    Date createdAt;

    Date updatedAt;
}
