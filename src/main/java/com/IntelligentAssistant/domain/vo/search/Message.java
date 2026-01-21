package com.IntelligentAssistant.domain.vo.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author thpaperman
 * @Description 模型生成的消息
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /* 完成内容 **/
    private String content;

    /* 角色（固定为assistant）**/
    private String role;

    /*  推理内容 **/
    private String reasoningContent;
}
