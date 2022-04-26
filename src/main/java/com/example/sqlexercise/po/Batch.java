package com.example.sqlexercise.po;

import java.util.Date;

public class Batch {

    String id;
    String batch_text;
    String user_id;
    Date created_at;
    Date updated_at;
    int main_id;
    int sub_id;

    public int getMain_id() {
        return main_id;
    }

    public String getBatch_text() {
        return batch_text;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getSub_id() {
        return sub_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBatch_text(String batch_text) {
        this.batch_text = batch_text;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
