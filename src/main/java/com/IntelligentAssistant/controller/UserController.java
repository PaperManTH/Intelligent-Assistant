package com.IntelligentAssistant.controller;

import com.IntelligentAssistant.domain.Result;
import com.IntelligentAssistant.domain.dto.LoginRequest;
import com.IntelligentAssistant.domain.dto.RegisterRequest;
import com.IntelligentAssistant.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Author thpaperman
 * @Description 用户 业务层
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * ✅登录
     *
     * @param loginRequest 登录请求
     * @return {@link Result}
     */
    @PostMapping("/login")
    public Result login(@RequestBody  LoginRequest loginRequest) {
        Result result = new Result();
        result.put("token", userService.login(loginRequest));
        return result;
    }

    /**
     * ✅注册
     *
     * @param registerRequest 注册请求
     * @return {@link Result}
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterRequest registerRequest) {
        Result result = new Result();
        result.put("token", userService.register(registerRequest));
        return result;
    }

    /**
     * ✅登出
     *
     * @return {@link Result}
     */
    @PostMapping("/logout")
    public Result logout() {
        userService.logout();
        return Result.success("登出成功");
    }

    /**
     * ✅获取验证码
     *
     * @return {@link Result}
     */
    @PostMapping("/captchaCode")
    public Result captchaCode() {
        Result result = new Result();
        Map<String, String> captchaData = userService.captcha();
        result.put("uuid", captchaData.get("uuid"));
        result.put("captchaBase64", captchaData.get("captchaBase64"));
        return result;
    }
}
