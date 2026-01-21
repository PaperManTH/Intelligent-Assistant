package com.IntelligentAssistant.utils;

import com.IntelligentAssistant.domain.vo.weather.WeatherResponse;
import com.IntelligentAssistant.mapper.CityMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @Author thpaperman
 * @Description 天气查询工具类
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Component
@Slf4j
public class WeatherUtil {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${gaode.api-key}")
    private String apiKey;

    /**
     *
     *
     * @param cityName 城市名
     * @param queryType 查询类型
     * @return {@link WeatherResponse}
     */
    public WeatherResponse getWeather(String cityName, String queryType) {
        long start = System.currentTimeMillis();
        try {
            WeatherResponse response = queryWeather(cityName, queryType);
            log.info("天气查询成功 city={}, extensions={}, 耗时={}ms",
                    cityName, queryType,
                    System.currentTimeMillis() - start);
            return response;
        } catch (Exception e) {
            log.error("天气查询失败 city={}, extensions={}, 耗时={}ms, 错误={}",
                    cityName, queryType,
                    System.currentTimeMillis() - start, e.getMessage(), e);
            throw new RuntimeException("天气查询失败，请稍后再试", e);
        }
    }

    public WeatherResponse queryWeather(String cityName, String queryType) {
        String adCode = cityMapper.getCityCodeByName(cityName);
        String url = UriComponentsBuilder.fromUriString("https://restapi.amap.com/v3/weather/weatherInfo")
                .queryParam("key", apiKey)
                .queryParam("output", "json")
                .queryParam("city", adCode)
                .queryParam("extensions", queryType)
                .toUriString();

        String weatherJson = restTemplate.getForObject(url, String.class);
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            WeatherResponse response = objectMapper.readValue(weatherJson, new TypeReference<>() {});
            if (!"1".equals(response.getStatus())) {
                throw new RuntimeException("高德 API 调用失败: " + response.getInfo());
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException("解析高德天气响应失败", e);
        }
    }
}
