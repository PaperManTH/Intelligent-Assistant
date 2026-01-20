package com.IntelligentAssistant.service;

import com.IntelligentAssistant.domain.dto.ChatDTO;
import com.IntelligentAssistant.domain.entity.AIChatMessage;
import com.IntelligentAssistant.domain.vo.AIHistoryVO;
import com.IntelligentAssistant.domain.vo.ChatResponse;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @Author thpaperman
 * @Description 聊天业务接口
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

public interface IChatService {

    /**
     * 聊天
     *
     * @param chatDTO 聊天参数
     * @return {@link Flux<ChatResponse>}
     */
    Flux<ChatResponse> chat(ChatDTO chatDTO);

    /**
     * 获取历史记录
     *
     * @return {@link List<AIHistoryVO>}
     */
    List<AIHistoryVO> getConversation();

    /**
     * 获取历史会话内容
     *
     * @param conversationId 会话ID
     * @return {@link List<AIChatMessage>}
     */
    List<AIChatMessage> getHistoryContent(String conversationId);

    /**
     * 删除历史记录
     *
     * @param conversationId 会话ID
     */
    void deleteConversation(String conversationId);
}
