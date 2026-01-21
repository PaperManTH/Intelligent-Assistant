package com.IntelligentAssistant.domain.vo.weather;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author thpaperman
 * @Description 天气查询返回结果详细信息
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherLives {

    /** 省份名 **/
    private String province;

    /** 城市名 **/
    private String city;

    /** 地区编码 **/
    private String adcode;

    /** 数据发布时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("reporttime")
    private String reportTime;

    /** 天气 **/
    private String weather;

    /** 温度 **/
    private String temperature;

    /** 风向 **/
    @JsonProperty("winddirection")
    private String windDirection;

    /** 风力级别 **/
    @JsonProperty("windpower")
    private String windPower;

    /** 相对湿度 **/
    private String humidity;

    /** 实时气温，单位：摄氏度 对应浮点数 **/
    @JsonProperty("temperature_float")
    private String temperatureFloat;

    /** 湿度，单位：百分比 对应浮点数 **/
    @JsonProperty("humidity_float")
    private String humidityFloat;
}
