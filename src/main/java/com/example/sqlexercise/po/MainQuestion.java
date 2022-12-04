package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class MainQuestion {

    Integer id;

    String title;

    String description;

    String dbPath;

    String fileName;

    Date createdAt;

    Date updatedAt;

    int totalDifficulty;

    int subCount;
}
