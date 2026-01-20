package com.IntelligentAssistant.service.impl;

import com.IntelligentAssistant.constant.UserConstant;
import com.IntelligentAssistant.domain.UserContext;
import com.IntelligentAssistant.domain.dto.LoginRequest;
import com.IntelligentAssistant.domain.dto.RegisterRequest;
import com.IntelligentAssistant.domain.entity.IaUser;
import com.IntelligentAssistant.exception.user.*;
import com.IntelligentAssistant.mapper.IaUserMapper;
import com.IntelligentAssistant.security.domain.LoginUser;
import com.IntelligentAssistant.service.IUserService;
import com.IntelligentAssistant.utils.JwtUtil;
import com.IntelligentAssistant.utils.RedisCache;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.IntelligentAssistant.constant.RedisCacheConstant.CAPTCHA_CODES;
import static com.IntelligentAssistant.constant.RedisCacheConstant.LOGIN_TOKEN_KEY;
import static com.IntelligentAssistant.constant.UserConstant.CAPTCHA_EXPIRE_TIME;
import static com.IntelligentAssistant.constant.UserConstant.TOKEN_EXPIRE_TIME;

/**
 * @Author thpaperman
 * @Description 用户 业务层实现类
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<IaUserMapper, IaUser> implements IUserService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * 登录
     *
     * @param loginRequest 登录请求参数
     * @return {@link String}
     */
    @Override
    public String login(LoginRequest loginRequest) {
        String username = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        // 1. 参数校验
        //validateCode(loginRequest.getCode(), loginRequest.getUuid());
        validatePassword(username, password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 2. 生成JWT令牌
        String token = JwtUtil.createJwt(JSON.toJSONString(loginUser));
        // 3. 将token存入Redis
        String userKey = LOGIN_TOKEN_KEY + username;
        redisCache.setCache(userKey, token, TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        // 4. 存入上下文中
        UserContext.setCurrentUser(loginUser.getUser());
        return token;
    }

    /**
     * 注册
     *
     * @param registerRequest 注册请求参数
     * @return {@link String}
     */
    @Override
    public String register(RegisterRequest registerRequest) {
        String username = registerRequest.getUserName();
        String password = registerRequest.getPassword();
        // 1. 参数校验
        //validateCode(registerRequest.getCode(), registerRequest.getUuid());
        validateRegisterPassword(username, password);
        if (userExists(username)) {
            throw new UserAlreadyExistsException();
        }
        // 2. 保存用户信息
        IaUser iaUser = new IaUser();
        iaUser.setUserName(username);
        iaUser.setUserPassword(passwordEncoder.encode(password));
        iaUser.setCreateTime(LocalDateTime.now());
        save(iaUser);
        // 3. 生成JWT令牌
        String token = JwtUtil.createJwt(JSON.toJSONString(iaUser));
        // 4. 将 token 存入Redis
        String userKey = LOGIN_TOKEN_KEY + username;
        redisCache.setCache(userKey, token, TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        // 5. 存入上下文中
        UserContext.setCurrentUser(iaUser);
        return token;
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        IaUser iaUser = UserContext.getCurrentUser();
        if (ObjectUtils.isNotEmpty(iaUser)) {
            redisCache.delete(LOGIN_TOKEN_KEY + iaUser.getUserName());
            UserContext.clear();
        } else {
            throw new UserNotExistsException();
        }
    }

    /**
     * 获取验证码
     *
     * @return {@link Map}
     */
    @Override
    public Map<String, String> captcha() {
        String captchaText = captchaProducer.createText();
        log.info("验证码已生成：{}", captchaText);
        // 1. 使用 UUID 生成唯一 key
        String uuid = UUID.randomUUID().toString();
        // 2. 将验证码文本存入 Redis 并设置过期时间（默认 5 分钟）
        redisCache.setCache(CAPTCHA_CODES + uuid, captchaText, CAPTCHA_EXPIRE_TIME, TimeUnit.MINUTES);
        // 3. 生成验证码图像
        BufferedImage image = captchaProducer.createImage(captchaText);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            throw new CaptchaCreateException();
        }
        // 4. 转 Base64 返回
        String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
        return Map.of("uuid", uuid,
                "captchaBase64", "data:image/png;base64," + base64Image);
    }

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return boolean
     */
    private boolean userExists(String username) {
        return lambdaQuery()
                .eq(IaUser::getUserName, username)
                .exists();
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     */
    private void validateCode(String code, String uuid) {
        String verifyKey = CAPTCHA_CODES + uuid;
        String cacheCode;
        try {
            cacheCode = redisCache.getCache(verifyKey, String.class);
        } catch (Exception e) {
            throw new CaptchaExpireException();
        }
        redisCache.delete(verifyKey);
        if (!code.equalsIgnoreCase(cacheCode)) {
            throw new CaptchaException();
        }
    }

    /**
     * 校验登录密码
     *
     * @param username 用户名
     * @param password 密码
     */
    private void validatePassword(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new UserInfoNotNullException();
        }
        if (username.length() < UserConstant.USER_NAME_MIN_LENGTH
                || username.length() > UserConstant.USER_NAME_MAX_LENGTH) {
            throw new UserInfoFormatException();
        }
        if (password.length() < UserConstant.PASSWORD_MIN_LENGTH
                || password.length() > UserConstant.PASSWORD_MAX_LENGTH) {
            throw new UserPasswordException();
        }
    }

    /**
     * 校验注册密码
     *
     * @param username 用户名
     * @param password 密码
     */
    private void validateRegisterPassword(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new UserInfoNotNullException();
        }
        if (username.length() < UserConstant.USER_NAME_MIN_LENGTH
                || username.length() > UserConstant.USER_NAME_MAX_LENGTH) {
            throw new UserInfoFormatException();
        }
        if (password.length() < UserConstant.PASSWORD_MIN_LENGTH
                || password.length() > UserConstant.PASSWORD_MAX_LENGTH) {
            throw new UserInfoFormatException();
        }
    }

}
