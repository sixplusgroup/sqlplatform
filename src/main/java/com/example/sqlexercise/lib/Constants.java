package com.example.sqlexercise.lib;

/**
 * 常量类
 */
public class Constants {

    /**
     * Redis中key的前缀常量
     */
    public static class RedisKey {
        /**
         * 验证码前缀
         */
        public static final String CODE_KEY_PREFIX = "CODE:";
    }

    /**
     * 执行用户写的sql的模式，运行/提交
     */
    public static class ProcessSqlMode {
        public static final String RUN = "RUN";
        public static final String SUBMIT = "SUBMIT";
    }

    /**
     * Question状态值，未开始、提交未通过、已通过
     */
    public static class QuestionState {
        public static final int NOT_STARTED = 0;
        public static final int SUBMITTED_BUT_NOT_PASS = 1;
        public static final int PASSED = 2;
    }

    /**
     * Docker相关
     */
    public static class DockerRelated {
        /**
         * MySQL docker image     的名称、版本号
         * docker container 的默认端口
         */
        public static final String MYSQL_IMAGE_NAME = "mysql";
        public static final String MYSQL_IMAGE_TAG = "5.7";
        public static final String MYSQL_IMAGE = MYSQL_IMAGE_NAME + ":" + MYSQL_IMAGE_TAG;
        public static final int MYSQL_CONTAINER_DEFAULT_PORT = 3310;
        /**
         * Redis docker image     的名称、版本号
         * docker container 的默认端口
         */
        public static final String REDIS_IMAGE_NAME = "redis";
        public static final String REDIS_IMAGE_TAG = "7.0";
        public static final String REDIS_IMAGE = REDIS_IMAGE_NAME + ":" + REDIS_IMAGE_TAG;
        public static final int REDIS_CONTAINER_DEFAULT_PORT = 6379;
    }

    /**
     * 返回给前端的提示信息
     */
    public static class Message {
        /**
         * 验证码校验相关
         */
        public static final String CODE_EXPIRED = "验证码已过期";
        public static final String CODE_WRONG = "验证码错误";
        /**
         * 密码校验相关
         */
        public static final String PASSWORD_CONFIRMATION_WRONG = "两次输入的密码不一致";
        public static final String EMAIL_OR_PASSWORD_WRONG = "邮箱或密码错误";
        /**
         * 邮箱校验相关
         */
        public static final String EMAIL_EXISTED = "该邮箱已注册";
        public static final String EMAIL_NOT_EXISTED = "该邮箱未注册";
        /**
         * 成功失败相关
         */
        public static final String SIGNUP_SUCCEED = "注册成功";
        public static final String MODIFY_USER_INFO_SUCCEED = "修改个人信息成功";
        public static final String MODIFY_USER_INFO_FAILED = "修改个人信息失败";
        public static final String RESET_PASSWORD_SUCCEED = "重置密码成功";
        public static final String RESET_PASSWORD_FAILED = "重置密码失败";
        public static final String CODE_SEND_SUCCEED = "发送成功";
        public static final String SAVE_DRAFT_SUCCEED = "保存成功";
        public static final String STAR_SUCCEED = "已收藏";
        public static final String UNSTAR_SUCCEED = "取消收藏";
        /**
         * SQL运行相关
         */
        public static final String PASSED = "Passed";   // SQL运行通过
        public static final String NOT_PASSED = "Didn't pass";   // SQL运行未通过
    }

}
