package com.example.sqlexercise.vo;

public class UserVO {
    String name;
    String email;
    String code;
    String password;
    String passwordComfirmation;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordComfirmation() {
        return passwordComfirmation;
    }

    public void setPasswordComfirmation(String passwordComfirmation) {
        this.passwordComfirmation = passwordComfirmation;
    }
}
