package com.phpsong.common.exception;

import com.phpsong.common.enums.ExceptionEnum;
import lombok.Getter;

/**
 * @author bystander
 * @date 2018/9/15
 * <p>
 * 自定义异常类
 */
@Getter
public class ShopException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

    public ShopException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }


}
