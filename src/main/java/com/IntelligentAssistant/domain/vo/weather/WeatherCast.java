package com.IntelligentAssistant.domain.vo.weather;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author thpaperman
 * @Description 天气预报
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherCast {

    /** 天气预报日期 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String date;

    /** 天气预报星期 **/
    private String week;

    /** 天气预报白天天气 **/
    @JsonProperty("dayweather")
    private String dayWeather;

    /** 天气预报夜间天气 **/
    @JsonProperty("nightweather")
    private String nightWeather;

    /** 天气预报白天温度 **/
    @JsonProperty("daytemp")
    private String dayTemp;

    /** 天气预报夜间温度 **/
    @JsonProperty("nighttemp")
    private String nightTemp;

    /** 天气预报白天风向 **/
    @JsonProperty("daywind")
    private String dayWind;

    /** 天气预报夜间风向 **/
    @JsonProperty("nightwind")
    private String nightWind;

    /** 天气预报白天风力 **/
    @JsonProperty("daypower")
    private String dayPower;

    /** 天气预报夜间风力 **/
    @JsonProperty("nightpower")
    private String nightPower;
}
