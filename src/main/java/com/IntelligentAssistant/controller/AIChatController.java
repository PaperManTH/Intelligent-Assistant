package com.IntelligentAssistant.controller;

import com.IntelligentAssistant.domain.Result;
import com.IntelligentAssistant.domain.dto.ChatDTO;
import com.IntelligentAssistant.domain.vo.ChatResponse;
import com.IntelligentAssistant.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author thpaperman
 * @Description 对话 业务层
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@RestController
@RequestMapping("/aiChat")
public class AIChatController {

    @Autowired
    private IChatService chatService;

    /**
     * ✅对话
     *
     * @param dto 对话参数
     * @return {@link Flux<ChatResponse>}
     */
    @PostMapping("/chat")
    public Flux<ChatResponse> chat(@RequestBody @Valid ChatDTO dto) {
        return chatService.chat(dto);
    }

    /**
     * ✅获取历史会话
     *
     * @return {@link Result}
     */
    @GetMapping("/getConversation")
    public Result getConversation() {
        return Result.success(chatService.getConversation());
    }

    /**
     * ✅获取历史会话内容
     *
     * @param conversationId 会话ID
     * @return {@link Result}
     */
    @GetMapping("/getContext")
    public Result getContext(@RequestParam @NotNull String conversationId) {
        return Result.success(chatService.getHistoryContent(conversationId));
    }

    /**
     * ✅删除历史会话
     *
     * @param conversationId 会话ID
     * @return {@link Result}
     */
    @PostMapping("/deleteConversation")
    public Result deleteConversation(@RequestParam @NotNull String conversationId) {
        chatService.deleteConversation(conversationId);
        return Result.success();
    }
}
