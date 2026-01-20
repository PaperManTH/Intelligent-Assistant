package com.IntelligentAssistant.tool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * @Author thpaperman
 * @Description  天气、空气质量、新闻等外部服务方法
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalApiTool {

    @Tool(name = "checkWeather", description = "中国境内（实时/预报）天气信息查询")
    public String checkWeather(
            @ToolParam(description = "城市名称") String cityName,
            @ToolParam(description = "查询类型 base:实况/all:预报") String queryType) {
        return null;
    }
}
