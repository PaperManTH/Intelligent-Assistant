package com.IntelligentAssistant.constant;

/**
 * @Author thpaperman
 * @Description Redis 缓存常量类
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */
public class RedisCacheConstant {

    /** 项目前缀 **/
    public static final String PROJECT_PREFIX = "aiChat:";

    /** 用户 TOKEN **/
    public static final String LOGIN_TOKEN_KEY = PROJECT_PREFIX + "user:login_token:";

    /** 用户验证码 **/
    public static final String CAPTCHA_CODES = PROJECT_PREFIX + "user:captcha_codes:";

    /** Redis 长期记忆前缀 **/
    public static final String MEMORY_KEY_PREFIX = PROJECT_PREFIX + "ChatHistory:";

}
