package com.IntelligentAssistant.exception.user;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 验证码失效异常
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */
public class CaptchaExpireException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException() {
        super("验证码已失效", null);
        logError();
    }
}
