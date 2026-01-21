package com.IntelligentAssistant.domain.vo.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author thpaperman
 * @Description 天气查询返回结果
 * @Date 2026/1/21
 * @DAY_NAME_FULL: 星期三
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {

    /** 状态 **/
    private String status;

    /** 数量 **/
    private String count;

    /** 信息 **/
    private String info;

    /** 返回状态说明,10000 代表正确 **/
    private String infocode;

    /** 详细信息（实时） **/
    private List<WeatherLives> lives;

    /** 详细信息（预报） **/
    private List<WeatherForecast> forecasts;

}
