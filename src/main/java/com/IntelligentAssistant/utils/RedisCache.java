package com.IntelligentAssistant.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author thpaperman
 * @Description redis 缓存工具类
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */

@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class RedisCache {

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 设置缓存(永久)
     *
     * @param key   缓存的 key
     * @param value 缓存的 value
     */
    public <T> void setCache(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存(带过期时间)
     *
     * @param key         缓存的 key
     * @param value       缓存的 value
     * @param timeout     过期时间
     * @param timeoutUnit 时间单位
     */
    public <T> void setCache(final String key, final T value, final long timeout, final TimeUnit timeoutUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeoutUnit);
    }

    /**
     * 缓存续期
     *
     * @param key         缓存的 key
     * @param timeout     过期时间
     * @param timeoutUnit 时间单位
     * @return {@link Boolean}
     */
    public boolean expire(final String key, final long timeout, final TimeUnit timeoutUnit) {
        return redisTemplate.expire(key, timeout, timeoutUnit);
    }

    /**
     * 获取缓存过期时间
     *
     * @param key 缓存的 key
     * @return {@link Long}
     */
    public long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断缓存是否存在
     *
     * @param key 缓存的 key
     * @return {@link Boolean}
     */
    public boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取缓存值
     *
     * @param key   缓存的 key
     * @param clazz 缓存的 value 的类型
     * @return {@link T}
     */
    public <T> T getCache(final String key, final Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        if (clazz.isInstance(value)) {
            return (T) value;
        }
        // 使用 ObjectMapper 进行类型转换
        return objectMapper.convertValue(value, clazz);
    }

    /**
     * 删除缓存
     *
     * @param key 缓存的 key
     */
    public void delete(final String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     *
     * @param keys 缓存的 key 集合
     * @return {@link Boolean}
     */
    public boolean deleteList(final Collection<String> keys) {
        return redisTemplate.delete(keys) > 0L;
    }

    /**
     * 向列表右侧添加元素
     *
     * @param key 缓存的 key
     * @param value 缓存的 value
     */
    public void pushToListRight(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 获取列表范围
     *
     * @param key   缓存的 key
     * @param start 列表的起始位置
     * @param end   列表的结束位置
     * @return {@link List<String>}
     */
    public List<String> getListRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 裁剪列表
     *
     * @param key 缓存的 key
     * @param start 列表的起始位置
     * @param end 列表的结束位置
     */
    public void trimList(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key 缓存的 key
     * @return {@link Long}
     */
    public Long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 获取匹配的键
     *
     * @param pattern 匹配模式
     * @return {@link Set<String>}
     */
    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

}
