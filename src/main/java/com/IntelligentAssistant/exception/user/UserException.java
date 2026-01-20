package com.IntelligentAssistant.exception.user;

import com.IntelligentAssistant.exception.BaseException;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 用户模块异常类
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */
public class UserException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserException(String message, Object[] args) {
        super("user", message, args, null);
        logError();
    }
}
