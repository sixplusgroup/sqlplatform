package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class Batch {

    String id;

    String batchText;

    String userId;

    Date createdAt;

    Date updatedAt;

    int mainId;

    int subId;
}
