package com.IntelligentAssistant.exception.chat;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description 聊天流异常
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */
public class ChatStreamException extends ChatException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ChatStreamException() {
        super("聊天异常 已终止", null);
    }
}
