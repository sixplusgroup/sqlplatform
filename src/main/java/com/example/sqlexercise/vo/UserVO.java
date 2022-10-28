package com.example.sqlexercise.vo;

import lombok.Data;

@Data
public class UserVO {
    private String name;
    private String email;
    private String code;
    private String password;
    private String passwordConfirmation;
}
