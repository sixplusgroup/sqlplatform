package com.example.sqlexercise.vo;

import lombok.Data;

@Data
public class ResponseVO {

    private String res;
    private String msg;
    private Object obj;

    public ResponseVO(String res, String msg) {
        this.res = res;
        this.msg = msg;
    }

    public ResponseVO(String res, Object obj) {
        this.res = res;
        this.obj = obj;
    }

    public static ResponseVO success(String msg) {
        return new ResponseVO("success", msg);
    }

    public static ResponseVO failure(String msg) {
        return new ResponseVO("failure", msg);
    }

    public static ResponseVO success(Object obj) {
        return new ResponseVO("success", obj);
    }
}
