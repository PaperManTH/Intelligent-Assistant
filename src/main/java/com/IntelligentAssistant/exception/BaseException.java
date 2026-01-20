package com.IntelligentAssistant.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

/**
 * @Author thpaperman
 * @Description Brain-box 异常基类
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */

@AllArgsConstructor
@Getter
@Slf4j
public class BaseException extends RuntimeException {

    /** 所属模块 **/
    private String module;

    /** 错误码 **/
    private String code;

    /** 错误码对应的参数 **/
    private Object[] args;

    /** 错误消息 **/
    private String defaultMessage;

    @Serial
    private static final long serialVersionUID = 1L;

    public BaseException(String module, String code, Object[] args) { this(module, code, args, null); }

    public BaseException(String module, String defaultMessage) { this(module, null, null, defaultMessage); }

    public BaseException(String code, Object[] args) { this(null, code, args, null); }

    public BaseException(String defaultMessage) { this(null, null, null, defaultMessage); }

    /**
     * 错误日志
     */
    public void logError() {
        log.error("异常发生: 模块={}, 错误码={}, 参数={}, 默认消息={}",
                module, code, args, defaultMessage);
    }

    /**
     * 错误日志
     *
     * @param customMessage 自定义错误消息
     */
    public void logError(String customMessage) {
        log.error("异常发生: {}, 模块={}, 错误码={}, 参数={}, 默认消息={}",
                customMessage, module, code, args, defaultMessage);
    }
}
