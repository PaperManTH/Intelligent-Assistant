package com.IntelligentAssistant.service.impl;

import com.IntelligentAssistant.domain.UserContext;
import com.IntelligentAssistant.domain.dto.ChatDTO;
import com.IntelligentAssistant.domain.entity.AIChatMessage;
import com.IntelligentAssistant.domain.entity.AiConversation;
import com.IntelligentAssistant.domain.vo.AIHistoryVO;
import com.IntelligentAssistant.domain.vo.ChatResponse;
import com.IntelligentAssistant.repository.RedisConversationStore;
import com.IntelligentAssistant.service.IChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author thpaperman
 * @Description 聊天业务接口实现类
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Service
public class ChatServiceImpl implements IChatService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private RedisConversationStore conversationStore;

    /**
     * 聊天
     *
     * @param chatDTO 聊天参数
     * @return {@link Flux<ChatResponse>}
     */
    @Override
    public Flux<ChatResponse> chat(ChatDTO chatDTO) {
        String userId = UserContext.getUserId();
        String conversationId = chatDTO.getConversationId();
        String question = chatDTO.getQuestion();

        if (conversationId == null || conversationId.isEmpty()) {
            // 创建一个新会话
            String title = question.length() > 10 ? question.substring(0, 10) : question;
            conversationId = conversationStore.createConversation(userId, title).getConversationId();
        }
        // 将用户输入持久化到 memory
        saveMemory(question, conversationId, "USER", userId);

        // 1. 累积完整回答，等流结束统一写 memory
        StringBuilder answerBuffer = new StringBuilder();
        String finalConversationId = conversationId;
        return chatClient.prompt()
                .messages(new UserMessage(question))
                // 2. 顾问模式自动注入短期记忆
                .advisors(advisorSpec ->
                        advisorSpec.param(ChatMemory.CONVERSATION_ID, finalConversationId))
                .stream()
                .content()
                .map(content -> {
                    // 累积完整结果
                    answerBuffer.append(content);
                    return ChatResponse.builder()
                            .text(content)
                            .status("SUCCESS")
                            .conversationId(finalConversationId)
                            .timestamp(System.currentTimeMillis())
                            .build();
                })
                .doOnComplete(() -> {
                    // 4. 在流结束后写入一次 memory
                    saveMemory(answerBuffer.toString(), finalConversationId, "ASSISTANT", userId);
                })
                .doOnError(e -> {
                    throw new RuntimeException("聊天异常,已终止", e);
                });
    }

    /**
     * 更新 memory
     *
     * @param text           文本
     * @param conversationId 会话ID
     * @param role           角色
     */
    private void saveMemory(String text, String conversationId, String role, String userId) {
        AIChatMessage message = AIChatMessage.builder()
                .content(text)
                .role(role)
                .timestamp(System.currentTimeMillis())
                .build();
        conversationStore.updateMessage(message, conversationId, userId);
    }


    /**
     * 获取历史会话
     *
     * @return {@link List<AIHistoryVO>}
     */
    @Override
    public List<AIHistoryVO> getConversation() {
        String userId = UserContext.getUserId();
        List<AiConversation> conversations = conversationStore.loadHistoryByUser(userId);
        // 时间戳转换为 yyyy-MM-dd HH:mm:ss 格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return conversations.stream().map(conversation ->
                AIHistoryVO.builder()
                        .conversationId(conversation.getConversationId())
                        .title(conversation.getTitle())
                        .updateTime(sdf.format(Date.from(conversation.getUpdateTime())))
                        .build()
        ).toList();
    }

    /**
     * 获取历史会话内容
     *
     * @param conversationId 会话ID
     * @return {@link List<AIChatMessage>}
     */
    @Override
    public List<AIChatMessage> getHistoryContent(String conversationId) {
        return conversationStore.loadHistory(conversationId, UserContext.getUserId());
    }

    /**
     * 删除历史会话
     *
     * @param conversationId 会话ID
     */
    @Override
    public void deleteConversation(String conversationId) {
        conversationStore.deleteHistory(conversationId, UserContext.getUserId());
    }
}
