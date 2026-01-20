package com.IntelligentAssistant.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.parser.Authorization;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @Author thpaperman
 * @Description jwt 工具类
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */
public class JwtUtil {

    private static final Long JWT_TTL = 60 * 60 * 1000L;
    private static final String JWT_KEY = "c3VwZXItc2VjcmV0LWtleS1mb3Itand0LXNpZ25pbmcta2V5LWZvci1oczI1Ng";
//    private static final String JWT_KEY = System.getenv("JWT_KEY")
    private static final String BEARER_PREFIX = "Ia ";

    /**
     * 生成 uuid
     *
     * @return {@link String}
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成 jwt
     *
     * @param subject token 中要存放的数据
     * @return {@link String}
     */
    public static String createJwt(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, getUUID());
        return builder.compact();
    }

    /**
     * 生成 jwt
     *
     * @param subject token 中要存放的数据
     * @param uuid    唯一标识
     * @return {@link JwtBuilder}
     */
    private static JwtBuilder getJwtBuilder(String subject, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + JWT_TTL;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("th")
                .setIssuedAt(now)
                .signWith(secretKey, signatureAlgorithm)
                .setExpiration(expDate);
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return {@link SecretKey}
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
    }

    /**
     * 解析 jwt
     *
     * @param jwt jwt
     * @return {@link Claims}
     */
    public static Claims parseJwt(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 从请求头中获取 token
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * 校验 token 是否有效
     *
     * @param token 待校验的 token
     * @return boolean true表示有效，false表示无效
     */
    public static boolean validateToken(String token) {
        try {
            Claims claims = parseJwt(token);
            // 检查是否已过期
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 token 中获取用户信息
     *
     * @param token token
     * @return Claims 包含用户信息的Claims对象
     */
    public static Claims getClaimsFromToken(String token) {
        try {
            return parseJwt(token);
        } catch (Exception e) {
            return null;
        }
    }
}
