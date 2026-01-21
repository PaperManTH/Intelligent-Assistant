package com.IntelligentAssistant.domain.vo.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author thpaperman
 * @Description 搜索引用
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reference {

    /* 站点图标 **/
    private String icon;

    /* 网页编号 **/
    private Integer id;

    /* 网页标题 **/
    private String title;

    /* 网页地址 **/
    private String url;

    /* 网页内容 **/
    private String content;

    /* 网页日期 **/
    private String date;

    /* 资源类型 **/
    private String type;

    /* 站点名称 **/
    private String website;
}
