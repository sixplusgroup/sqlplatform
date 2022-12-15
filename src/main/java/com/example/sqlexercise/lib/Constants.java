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

}
