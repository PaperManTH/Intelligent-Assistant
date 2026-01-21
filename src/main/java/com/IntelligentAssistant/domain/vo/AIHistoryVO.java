package com.IntelligentAssistant.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Author thpaperman
 * @Description AI历史记录VO
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Data
@Builder
public class AIHistoryVO {

    /** 会话ID **/
    private String conversationId;

    /** 会话标题 **/
    private String title;;

    /** 会话更新时间 **/
    private String updateTime;
}
