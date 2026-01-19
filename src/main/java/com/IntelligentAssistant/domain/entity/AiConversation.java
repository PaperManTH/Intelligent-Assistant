package com.IntelligentAssistant.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author thpaperman
 * @Description TODO
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Data
public class AiConversation implements Serializable {
    /**
     * 会话 Id
     */
    @Id
    private String conversationId;
    /**
     * 会话关联用户Id
     */
    private Integer userId;
    /**
     * 会话标题
     */
    private String title;
    /**
     * 会话消息,初始化为空
     */
    private List<AIChatMessage> messages = new ArrayList<>();
    /**
     * 会话创建时间
     */
    private Instant createTime;
    /**
     * 会话更新时间
     */
    private Instant updateTime;
    /**
     * 序列化版本号
     */
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 添加一条消息
     */
    public void addMessage(AIChatMessage message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        updateTime = Instant.now();
    }

    /**
     * 获取消息列表
     */
    public List<AIChatMessage> getMessages() {
        if (CollectionUtils.isEmpty(messages)) {
            return List.of();
        }
        return messages;
    }
}
