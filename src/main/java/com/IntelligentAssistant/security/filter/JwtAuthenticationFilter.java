package com.IntelligentAssistant.security.filter;

import com.IntelligentAssistant.domain.UserContext;
import com.IntelligentAssistant.security.domain.LoginUser;
import com.IntelligentAssistant.security.service.UserDetailsServiceImpl;
import com.IntelligentAssistant.utils.JwtUtil;
import com.IntelligentAssistant.utils.RedisCache;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.IntelligentAssistant.constant.RedisCacheConstant.LOGIN_TOKEN_KEY;

/**
 * @Author thpaperman
 * @Description JWT 认证过滤器
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RedisCache redisCache;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        // 匹配你要开放的匿名路径
        return path.equals("/user/login")
                || path.equals("/user/register")
                || path.equals("/user/captchaCode");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. 从请求头取 token
        String token = JwtUtil.getTokenFromHeader(request);
        // 2. 如果没有 token，则直接放行
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        // 3. 如果有 token，则做校验逻辑
        Claims claims = JwtUtil.parseJwt(token);
        if (claims != null && JwtUtil.validateToken(token)) {
            LoginUser loginUser = JSON.parseObject(claims.getSubject(), LoginUser.class);
            // 4. 从 Redis 校验 token 是否有效
            String redisToken = redisCache.getCache(LOGIN_TOKEN_KEY + loginUser.getUser().getUserName(), String.class);
            if (redisToken != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUser().getUserName());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserContext.setCurrentUser(loginUser.getUser());
            }
        }

        filterChain.doFilter(request, response);
    }
}
