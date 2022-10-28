package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class Cache {
    int id;
    String code;
    String email;
    Date expiryAt;
    Date createdAt;
}
