package com.IntelligentAssistant.utils;

import com.IntelligentAssistant.domain.vo.search.SearchResponse;
import com.IntelligentAssistant.domain.vo.weather.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Author thpaperman
 * @Description 天气查询工具类
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Component
@Slf4j
public class WebSearchUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${baidu.api-key}")
    private String apiKey;

    /**
     * 百度智能搜索生成
     *
     * @param query 搜索关键词
     * @return {@link WeatherResponse}
     */
    public SearchResponse searching(String query) {
        long start = System.currentTimeMillis();
        try {
            SearchResponse response = searchInfo(query);
            log.info("联网查询成功 关键词={}, 耗时={}ms",
                    query, System.currentTimeMillis() - start);
            return response;
        } catch (Exception e) {
            log.error("联网查询失败 关键词={}, 耗时={}ms, 错误={}",
                    query, System.currentTimeMillis() - start, e.getMessage(), e);
            throw new RuntimeException("天气查询失败，请稍后再试", e);
        }
    }

    private SearchResponse searchInfo(String query) {
        // 构建请求 URL
        String url = "https://qianfan.baidubce.com/v2/ai_search/web_summary";
        // 构建请求体
        String requestBody = String.format("""
                {
                    "messages": [{"role": "user", "content": "%s"}],
                    "stream": false,
                    "resource_type_filter": [{"type": "web", "top_k": 1}],
                    "instruction": "请根据搜索结果总结回答, 内容精简不超过800字"
                }
                """, query);
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        // 创建请求实体
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class);
            String responseJson = responseEntity.getBody();
            return objectMapper.readValue(responseJson, SearchResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("解析搜索响应失败", e);
        }
    }
}
