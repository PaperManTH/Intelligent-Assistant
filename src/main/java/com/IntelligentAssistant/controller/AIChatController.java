package com.IntelligentAssistant.controller;

import com.IntelligentAssistant.domain.Result;
import com.IntelligentAssistant.domain.dto.ChatDTO;
import com.IntelligentAssistant.domain.vo.ChatVO;
import com.IntelligentAssistant.service.IChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

/**
 * @Author thpaperman
 * @Description 对话 业务层
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@RestController
@RequestMapping("/aiChat")
public class AIChatController {

    @Autowired
    private IChatService chatService;

    @PostMapping("/chat")
    public Flux<ChatVO> chat(@RequestBody @Valid ChatDTO dto) {
        return chatService.chat(dto);
    }

    @GetMapping("/getHistory")
    public Result history() {
        return Result.success(chatService.getHistory());
    }
    
    @GetMapping("/getContext")
    public Result getSession() {
        return Result.success(chatService.getSession());
    }



}
