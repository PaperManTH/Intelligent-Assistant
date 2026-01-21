package com.IntelligentAssistant.config;

import com.IntelligentAssistant.tool.ExternalApiTool;
import com.alibaba.cloud.ai.memory.redis.JedisRedisChatMemoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * @Author thpaperman
 * @Description Spring AI Alibaba 配置类
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Configuration
public class SpringAiAlibabaConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    @Value("${spring.ai.dashscope.memory-length}")
    private Integer memoryLen;

    @Value("classpath:Prompt_File/AISystemPrompt.txt")
    private Resource systemPromptResource;

    @Bean
    public ChatMemory chatMemory(JedisRedisChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(memoryLen)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel,
                                 ChatMemory chatMemory) {
        return ChatClient.builder(chatModel)
                // 配置系统提示词
                .defaultSystem(systemPromptResource)
                // 配置 MessageChatMemoryAdvisor 让 ChatClient 自动注入历史对话
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                // 配置工具调用默认工具
                .defaultTools(new ExternalApiTool())
                .build();
    }

}
