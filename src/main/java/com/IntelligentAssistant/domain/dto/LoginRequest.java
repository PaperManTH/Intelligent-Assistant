package com.IntelligentAssistant.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author thpaperman
 * @Description 登录请求参数
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */

@Data
public class LoginRequest {

    /** 用户名 **/
    private String userName;

    /** 密码 **/
    private String password;

    /** 验证码 定长 CODE_LENGTH **/
    private String code;

    /** 唯一标识, 用于匹配验证码 **/
    private String uuid;
}
