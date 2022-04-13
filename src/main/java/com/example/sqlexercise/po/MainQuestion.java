package com.example.sqlexercise.po;

import java.util.Date;

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
    int state;

    public int getId() {
        return id;
    }

    public String getDbPath() {
        return dbPath;
    }

    public String getDesc() {
        return desc;
    }

    public int getState() {
        return state;
    }

    public int getSubCount() {
        return subCount;
    }

    public int getTotalDifficulty() {
        return totalDifficulty;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTotalDifficulty(int totalDifficulty) {
        this.totalDifficulty = totalDifficulty;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
