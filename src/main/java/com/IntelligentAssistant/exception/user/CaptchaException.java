package com.IntelligentAssistant.exception.user;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 验证码验证失败异常
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */
public class CaptchaException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("验证码错误", null);
        logError();
    }
}
