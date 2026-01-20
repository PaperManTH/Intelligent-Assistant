package com.IntelligentAssistant.constant;

/**
 * @Author thpaperman
 * @Description 用户常量类
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */
public class UserConstant {

    /** 用户名最小长度 **/
    public static final Integer USER_NAME_MIN_LENGTH = 1;

    /** 用户名最大长度 **/
    public static final Integer USER_NAME_MAX_LENGTH = 7;

    /** 密码最小长度 **/
    public static final Integer PASSWORD_MIN_LENGTH = 6;

    /** 密码最大长度 **/
    public static final Integer PASSWORD_MAX_LENGTH = 20;

    /** 验证码有效时间 **/
    public static final Integer CAPTCHA_EXPIRE_TIME = 5;

    /** Token 有效时间 **/
    public static final Integer TOKEN_EXPIRE_TIME = 12 * 60;
}
