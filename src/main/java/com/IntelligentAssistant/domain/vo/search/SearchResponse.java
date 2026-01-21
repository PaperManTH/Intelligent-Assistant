package com.IntelligentAssistant.domain.vo.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author thpaperman
 * @Description 联网搜索 api 返回数据
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {

    /** request_id */
    private String requestId;

    /** 错误代码 */
    private String code;

    /** 错误消息 */
    private String message;

    /** 生成结果列表 */
    private List<Choice> choices;

    /** 搜索引用列表 */
    private List<Reference> references;

}
