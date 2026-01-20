package com.IntelligentAssistant.exception.user;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 用户不存在异常
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */
public class UserNotExistsException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("用户不存在", null);
        logError();
    }
}
