package com.example.sqlexercise.vo;

public class BatchVO {

    String batch_text;
    String user_id;
    int main_id;
    int sub_id;
    String driver;

    public String getUser_id() {
        return user_id;
    }

    public String getBatch_text() {
        return batch_text;
    }

    public int getSub_id() {
        return sub_id;
    }

    public int getMain_id() {
        return main_id;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setBatch_text(String batch_text) {
        this.batch_text = batch_text;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }

}
