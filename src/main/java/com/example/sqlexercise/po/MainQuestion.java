package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class MainQuestion {
    int id;
    String title;
    String desc;
    String dbPath;
    String fileName;
    Date createdAt;
    Date updatedAt;
    int totalDifficulty;
    int subCount;

}
