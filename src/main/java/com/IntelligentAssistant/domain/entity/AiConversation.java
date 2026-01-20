package com.IntelligentAssistant.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * @Author thpaperman
 * @Description TODO
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiConversation implements Serializable {
    /**
     * 序列化版本号
     */
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 会话 Id
     */
    @Id
    private String conversationId;
    /**
     * 会话关联用户Id
     */
    private String userId;
    /**
     * 会话标题
     */
    private String title;
    /**
     * 会话创建时间
     */
    private Instant createTime;
    /**
     * 会话更新时间
     */
    private Instant updateTime;
}
