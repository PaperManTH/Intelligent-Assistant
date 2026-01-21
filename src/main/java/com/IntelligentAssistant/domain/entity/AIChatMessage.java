package com.IntelligentAssistant.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author thpaperman
 * @Description 聊天记录
 * @Date 2025/4/30
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIChatMessage implements Serializable {

    /**
     * 聊天内容
     */
    private String content;
    /**
     * 角色
     */
    private String role;
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

}
