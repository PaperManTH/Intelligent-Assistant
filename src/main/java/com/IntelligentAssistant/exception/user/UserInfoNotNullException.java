package com.IntelligentAssistant.exception.user;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 用户注册信息不能为空
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */
public class UserInfoNotNullException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;
    public UserInfoNotNullException() {
        super("用户名或密码不为空", null);
        logError();
    }
}
