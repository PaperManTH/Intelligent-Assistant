package com.IntelligentAssistant.exception;

import com.IntelligentAssistant.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author thpaperman
 * @Description 全局异常处理类
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Result> handleUserException(BaseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Result.error(ex.getDefaultMessage()));
    }
}
