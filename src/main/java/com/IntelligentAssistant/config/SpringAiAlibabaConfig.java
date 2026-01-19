package com.paperman.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author thpaperman
 * @Description Spring AI Alibaba 配置类
 * @Date 2026/1/14
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Configuration
public class SpringAiAlibabaConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    /**
     * 配置 DashScopeApi
     *
     * @return {@link DashScopeApi}
     */
    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder().apiKey(apiKey).build();
    }

    @Bean
    public ChatModel chatModel(DashScopeApi scopeApi) {
        return DashScopeChatModel.builder().dashScopeApi(scopeApi).build();
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}
