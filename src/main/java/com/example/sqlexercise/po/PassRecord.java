package com.example.sqlexercise.po;

import java.util.Date;

public class PassRecord {
    int id;
    String userId;
    int mainId;
    int subId;
    String batchId;
    int point;
    Date createdAt;
    Date updatedAt;

    public int getMainId() {
        return mainId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSubId() {
        return subId;
    }

    public String getUserId() {
        return userId;
    }

    public int getPoint() {
        return point;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
