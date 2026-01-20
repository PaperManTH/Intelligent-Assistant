package com.IntelligentAssistant.repository;

import com.IntelligentAssistant.domain.entity.AIChatMessage;
import com.IntelligentAssistant.domain.entity.AiConversation;
import com.IntelligentAssistant.utils.RedisCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.IntelligentAssistant.constant.RedisCacheConstant.MEMORY_KEY_PREFIX;

/**
 * @Author thpaperman
 * @Description Redis 长期会话存储
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Repository
public class RedisConversationStore {

    // 最大历史消息数
    private static final int DEFAULT_MAX_MESSAGES = 100;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 创建一个新会话
     *
     * @param userId 用户ID
     * @return {@link AiConversation}
     */
    public AiConversation createConversation(String userId, String title) {
        AiConversation conv = AiConversation.builder()
                .conversationId(UUID.randomUUID().toString())
                .userId(userId)
                .title(title)
                .createTime(Instant.now())
                .updateTime(Instant.now())
                .build();
        // 存储会话元数据
        String metadataKey = getMetadataKey(userId, conv.getConversationId());
        redisCache.setCache(metadataKey, conv);
        // 初始化消息列表
        String messageKey = getRedisKey(userId, conv.getConversationId());
        redisCache.delete(messageKey);
        return conv;
    }

    /**
     * 更新会话消息
     *
     * @param message        消息
     * @param conversationId 会话ID
     * @param userId         用户ID
     */
    public void updateMessage(AIChatMessage message, String conversationId, String userId) {
        String key = getRedisKey(userId, conversationId);
        String metadataKey = getMetadataKey(userId, conversationId);
        try {
            String json = objectMapper.writeValueAsString(message);
            // 1. 追加消息到列表
            redisCache.pushToListRight(key, json);
            AiConversation metadata = redisCache.getCache(metadataKey, AiConversation.class);
            if (metadata != null) {
                metadata.setUpdateTime(Instant.now());
                redisCache.setCache(metadataKey, metadata);
            }

            // 2. 控制消息数量
            Long size = redisCache.getListSize(key);
            if (size != null && size > DEFAULT_MAX_MESSAGES) {
                redisCache.trimList(key, size - DEFAULT_MAX_MESSAGES, -1);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化消息失败", e);
        }
    }

    /**
     * 加载历史会话
     *
     * @param conversationId 会话ID
     * @param userId         用户ID
     * @return {@link List<AIChatMessage>}
     */
    public List<AIChatMessage> loadHistory(String conversationId, String userId) {
        String key = getRedisKey(userId, conversationId);
        List<String> jsonList = redisCache.getListRange(key, 0, -1);
        if (jsonList == null || jsonList.isEmpty()) {
            return List.of();
        }
        return jsonList.stream().map(json -> {
            try {
                return objectMapper.readValue(json, AIChatMessage.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("反序列化消息失败", e);
            }
        }).collect(Collectors.toList());
    }

    /**
     * 删除历史会话
     *
     * @param conversationId 会话ID
     * @param userId         用户ID
     */
    public void deleteHistory(String conversationId, String userId) {
        try {
            String metadataKey = getMetadataKey(userId, conversationId);
            String key = getRedisKey(userId, conversationId);
            redisCache.delete(metadataKey);
            redisCache.delete(key);
        } catch (Exception e) {
            throw new RuntimeException("删除历史会话失败", e);
        }
    }

    /**
     * 获取用户的所有会话
     *
     * @param userId 用户ID
     * @return {@link List<AiConversation>}
     */
    public List<AiConversation> loadHistoryByUser(String userId) {
        Set<String> metadataKeys = redisCache.getKeys(MEMORY_KEY_PREFIX + userId + ":convInfo"  + ":*");
        if (metadataKeys == null || metadataKeys.isEmpty()) {
            return List.of();
        }

        List<AiConversation> list = new ArrayList<>();
        for (String metadataKey : metadataKeys) {
            // 1. 获取会话元数据
            AiConversation metadata = redisCache.getCache(metadataKey, AiConversation.class);
            if (metadata != null) {
                // 2. 补充消息信息
                AiConversation conv = AiConversation.builder()
                        .conversationId(metadata.getConversationId())
                        .userId(metadata.getUserId())
                        .title(metadata.getTitle())
                        .createTime(metadata.getCreateTime())
                        .updateTime(metadata.getUpdateTime())
                        .build();
                list.add(conv);
            }
        }
        // 按更新时间倒序排列
        return list.stream()
                .sorted((a, b) -> b.getUpdateTime().compareTo(a.getUpdateTime()))
                .collect(Collectors.toList());
    }

    /**
     * 获取 Redis key
     *
     * @param userId         用户ID
     * @param conversationId 会话ID
     * @return {@link String}
     */
    private String getRedisKey(String userId, String conversationId) {
        // 前缀 + userId + 会话ID 作为 Redis key
        return MEMORY_KEY_PREFIX + userId + ":" + conversationId;
    }

    /**
     * 获取会话数据 Redis key
     *
     * @param userId 用户ID
     * @param conversationId 会话ID
     * @return {@link String}
     */
    private String getMetadataKey(String userId, String conversationId) {
        return MEMORY_KEY_PREFIX + userId + ":convInfo:" + conversationId;
    }
}
