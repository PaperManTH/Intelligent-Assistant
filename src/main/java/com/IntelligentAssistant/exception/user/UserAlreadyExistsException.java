package com.IntelligentAssistant.exception.user;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 用户已存在异常类
 * @Date 2026/1/4
 * @DAY_NAME_FULL: 星期日
 * @Version 1.0
 */
public class UserAlreadyExistsException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException() {
        super("用户名已存在", null);
        logError();
    }
}
