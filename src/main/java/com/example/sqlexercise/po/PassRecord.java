package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class PassRecord {

    Integer id;

    String userId;

    int mainId;

    int subId;

    String batchId;

    int point;

    Date createdAt;

    Date updatedAt;

    public PassRecord() {
    }

    public PassRecord(String userId, int mainId, int subId, String batchId, int point, Date createdAt, Date updatedAt) {
        this.userId = userId;
        this.mainId = mainId;
        this.subId = subId;
        this.batchId = batchId;
        this.point = point;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
