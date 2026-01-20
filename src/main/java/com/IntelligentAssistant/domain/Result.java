package com.IntelligentAssistant.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.HashMap;

/**
 * @Author thpaperman
 * @Description
 * @Date 2026/1/1
 * @DAY_NAME_FULL: 星期四
 * @Version 1.0
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result extends HashMap<String, Object> {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 状态码 **/
    private static final String STATUS_CODE = "code";

    /** 响应信息 **/
    private static final String MESSAGE = "msg";

    /** 返回数据 **/
    private static final String DATA = "data";

    /**
     * 构造函数, 初始化一个空返回结果对象
     */
    public Result() {
    }

    /**
     * 初始化Result对象, 带状态码和信息
     * @param status 状态码
     * @param msg 响应信息
     */
    public Result(HttpStatus status, String msg) {
        put(STATUS_CODE, status.value());
        put(MESSAGE, msg);
    }

    /**
     * 初始化Result对象, 带状态码, 信息和数据
     * @param status 状态码
     * @param msg 响应信息
     * @param data 响应数据
     */
    public Result(HttpStatus status, String msg, Object data) {
        put(STATUS_CODE, status.value());
        put(MESSAGE, msg);
        if(ObjectUtils.isNotEmpty(data)) {
            put(DATA, data);
        }
    }

    /**
     * 带状态码和信息的成功结果
     * @return Result
     */
    public static Result success() {
        return success("操作成功");
    }

    /**
     * 带状态码和信息的成功结果
     * @param data 响应数据
     * @return Result
     */
    public static Result success(Object data) {
        return success("操作成功", data);
    }

    /**
     * 带状态码和信息的成功结果
     * @param msg 响应信息
     * @return Result
     */
    public static Result success(String msg) {
        return new Result(HttpStatus.OK, msg);
    }

    /**
     * 带状态码和信息的成功结果
     * @param msg 响应信息
     * @param data 响应数据
     * @return Result
     */
    public static Result success(String msg, Object data) {
        return new Result(HttpStatus.OK, msg, data);
    }

    /**
     * 带状态码和信息的失败结果
     * @return Result
     */
    public static Result error() {
        return error("操作失败");
    }

    /**
     * 带状态码和信息的失败结果
     * @param data 响应数据
     * @return Result
     */
    public static Result error(Object data) {
        return error("操作失败", data);
    }

    /**
     * 带状态码和信息的失败结果
     * @param msg 响应信息
     * @return Result
     */
    public static Result error(String msg) {
        return new Result(HttpStatus.BAD_REQUEST, msg);
    }

    /**
     * 带状态码和信息的失败结果
     * @param msg 响应信息
     * @param data 响应数据
     * @return Result
     */
    public static Result error(String msg, Object data) {
        return new Result(HttpStatus.BAD_REQUEST, msg, data);
    }

}
