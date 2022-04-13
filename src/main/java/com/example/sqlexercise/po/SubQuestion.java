package com.example.sqlexercise.po;

import java.util.Date;

public class SubQuestion {
    int id;
    int mainId;
    String desc;
    String answer;
    boolean ordered;
    int difficulty;
    Date createdAt;
    Date updatedAt;

    public int getId() {
        return id;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public int getMainId() {
        return mainId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }
}
