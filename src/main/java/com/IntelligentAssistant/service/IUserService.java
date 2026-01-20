package com.IntelligentAssistant.service;

import com.IntelligentAssistant.domain.dto.LoginRequest;
import com.IntelligentAssistant.domain.dto.RegisterRequest;

import java.util.Map;

/**
 * @Author thpaperman
 * @Description 用户服务接口
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */
public interface IUserService {

    /**
     * 登录
     *
     * @param loginRequest 登录参数
     * @return {@link String}
     */
    String login(LoginRequest loginRequest);

    /**
     * 注册
     *
     * @param registerRequest 注册参数
     * @return {@link String}
     */
    String register(RegisterRequest registerRequest);

    /**
     * 登出
     */
    void logout();

    /**
     * 获取验证码
     *
     * @return {@link Map}
     */
    Map<String, String> captcha();
}
