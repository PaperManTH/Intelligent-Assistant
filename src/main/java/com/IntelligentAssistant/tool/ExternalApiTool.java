package com.IntelligentAssistant.tool;

import com.IntelligentAssistant.domain.vo.search.SearchResponse;
import com.IntelligentAssistant.domain.vo.weather.WeatherResponse;
import com.IntelligentAssistant.utils.WeatherUtil;
import com.IntelligentAssistant.utils.WebSearchUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author thpaperman
 * @Description  天气、空气质量、查询等外部服务方法
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalApiTool {

    @Autowired
    private WeatherUtil weatherUtil;

    @Autowired
    private WebSearchUtil searchUtil;

    /**
     * 天气查询工具
     *
     * @param cityName 城市名
     * @param queryType 查询类型
     * @return {@link String}
     */
    @Tool(name = "checkWeather", description = "中国境内（实时/预报）天气信息查询")
    public String checkWeather(
            @ToolParam(description = "城市名称") String cityName,
            @ToolParam(description = "查询类型 base:实况/all:预报") String queryType) {

        WeatherResponse weatherResponse = weatherUtil.getWeather(cityName, queryType);
        return String.format("查询 %s 的天气成功:【%s】", cityName, weatherResponse);
    }

    /**
     * 联网智能搜索工具
     *
     * @param query 关键词
     * @return {@link String}
     */
    @Tool(name = "webSearch", description = "联网智能搜索生成信息")
    public String webSearch(@ToolParam(description = "搜索关键词") String query) {
        // 调用搜索服务
        SearchResponse searchResponse = searchUtil.searching(query);
        return String.format("关键词 %s 搜索结果如下：%s", query, searchResponse);
    }
}
