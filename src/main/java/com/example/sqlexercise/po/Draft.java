package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class Draft {
    private int id;
    private String userId;
    private int mainId;
    private int subId;
    private String draft;
    private Date saveTime;
}
