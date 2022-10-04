package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

/**
 * @author shenyichen
 * @date 2022/9/20
 **/
@Data
public class Exam {
    String id;
    String examName;
    Date startTime;
    Date endTime;
    String questions;
    Boolean creatingStatus;
    String courseId;
    String notice;
    Date notice_created_at;
    Date createdAt;
    Date updatedAt;
}
