package com.phpsong.common.advice;

import com.phpsong.common.enums.ExceptionEnum;
import com.phpsong.common.exception.ShopException;
import com.phpsong.common.vo.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author bystander
 * @date 2018/9/15
 * <p>
 * 自定义异常处理
 */
@Slf4j
@ControllerAdvice
public class BasicExceptionHandler {

    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(ShopException.class)
    public ResponseEntity<ExceptionResult> handleException(ShopException e) {

        return ResponseEntity.status(e.getExceptionEnum().value())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }

    /**
     * 捕获全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResult> handleException(Exception e) {
        log.error("e={}",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(new ExceptionResult(ExceptionEnum.UNKNOW_ERROR));
    }
}
