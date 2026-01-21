package com.IntelligentAssistant.exception.captcha;

import com.IntelligentAssistant.exception.user.UserException;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 验证码生成失败异常
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */
public class CaptchaCreateException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaCreateException() {
        super("验证码生成失败", null);
    }
}
