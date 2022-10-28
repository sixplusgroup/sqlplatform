package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class PassRecord {
    int id;
    String userId;
    int mainId;
    int subId;
    String batchId;
    int point;
    Date createdAt;
    Date updatedAt;
}
