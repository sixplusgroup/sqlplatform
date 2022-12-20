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
         * 注册验证码前缀
         */
        public static final String SIGN_UP_CODE_KEY_PREFIX = "SIGNUP:CODE:";
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

}
