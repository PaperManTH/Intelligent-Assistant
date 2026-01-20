package com.IntelligentAssistant.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author thpaperman
 * @Description AI对话DTO类
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Data
public class ChatDTO {

    /** 问题 **/
    @NotNull
    private String question;

    /** 会话Id（新会话不需要传入id） **/
    private String conversationId;



}
