package com.IntelligentAssistant.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author thpaperman
 * @Description AI历史记录VO
 * @Date 2025/4/21
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Data
public class AIHistoryVO {

    /** 会话ID **/
    private String conversationId;

    /** 会话内容总结 **/
    private String firstQuestion;

    /** 会话时间 **/
    private String createTime;

    /** 会话答案 **/
    private List<String> answer;
}
