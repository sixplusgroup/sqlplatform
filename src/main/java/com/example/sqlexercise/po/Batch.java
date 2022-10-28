package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class Batch {

    String id;
    String batch_text;
    String user_id;
    Date created_at;
    Date updated_at;
    int main_id;
    int sub_id;
}
