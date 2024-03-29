package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    String id;
    String email;
    String role;
    String name;
    Boolean disabled;
    String passwordHash;
    Date createdAt;
    Date updatedAt;
}
