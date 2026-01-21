package com.IntelligentAssistant.domain.vo.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author thpaperman
 * @Description 生成结果
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choice {

    /** 模型生成的消息 */
    private Message message;

    /** 流式返回的增量内容 */
    private String delta;
}
