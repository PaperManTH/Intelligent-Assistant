package com.IntelligentAssistant.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.image.Image;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author thpaperman
 * @Description 聊天响应
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {

    /** 回复文本 **/
    private String text;

    /** 状态码 **/
    private String status;

    /** 所属会话Id **/
    private String conversationId;

    /** 元数据 **/
    private Map<String, Object> metadata;

    /** 时间戳 **/
    private Long timestamp;
}