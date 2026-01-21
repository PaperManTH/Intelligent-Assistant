package com.IntelligentAssistant.exception.captcha;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 验证码错误异常
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */
public class CaptchaErrorException extends CaptchaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaErrorException() {
        super("验证码校验失败", null);
    }
}
