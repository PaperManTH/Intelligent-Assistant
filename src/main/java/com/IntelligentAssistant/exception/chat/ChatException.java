package com.IntelligentAssistant.exception.chat;

import com.IntelligentAssistant.exception.BaseException;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 聊天异常
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */
public class ChatException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ChatException(String message, Object[] args) {
        super("chat", message, args, null);
    }
}
