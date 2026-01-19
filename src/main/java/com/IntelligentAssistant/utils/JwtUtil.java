package com.paperman.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @Author thpaperman
 * @Description JWT 工具类
 * @Date 2026/1/3
 * @DAY_NAME_FULL: 星期六
 * @Version 1.0
 */
public class JwtUtil {

    public static final Long JWT_TTL = 60 * 60 * 1000L;
    public static final String JWT_KEY = "sangeng";

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
     * @param subject token中要存放的数据（json格式）
     * @return {@link String}
     * @author thpaperman
     * @date 2026/01/05 18:20
     */
    public static String createJwt(String subject) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    /**
     * 生成 jwt
     *
     * @param subject   token 中要存放的数据（json格式）
     * @param ttlMillis token 超时时间
     * @return {@link String}
     */
    public static String createJwt(String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * 生成 jwt
     *
     * @param subject   token 中要存放的数据（json格式）
     * @param ttlMillis token 超时时间
     * @param uuid      唯一标识
     * @return {@link JwtBuilder}
     * @author thpaperman
     * @date 2026/01/05 19:27
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis,
                                            String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("sg")
                .setIssuedAt(now)
                .signWith(secretKey, signatureAlgorithm)
                .setExpiration(expDate);
    }

    /**
     * 创建 token
     *
     * @param id 唯一标识
     * @param subject 主题
     * @param ttlMillis 过期时间
     * @return {@link String}
     */
    public static String createJwt(String id, String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return {@link SecretKey}
     * @author thpaperman
     * @date 2026/01/05 19:16
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析 jwt
     *
     * @param jwt jwt
     * @return {@link Claims}
     * @author thpaperman
     * @date 2026/01/05 19:16
     */
    public static Claims parseJwt(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}