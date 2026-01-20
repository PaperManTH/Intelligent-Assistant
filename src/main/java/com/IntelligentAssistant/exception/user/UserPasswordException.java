package com.IntelligentAssistant.exception.user;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 用户密码错误异常类
 * @Date 2026/1/4
 * @DAY_NAME_FULL: 星期日
 * @Version 1.0
 */
public class UserPasswordException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserPasswordException() {
        super("用户名密码不匹配", null);
        logError();
    }
}
