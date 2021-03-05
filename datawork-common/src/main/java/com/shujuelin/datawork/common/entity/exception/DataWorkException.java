package com.shujuelin.datawork.common.entity.exception;

import lombok.Getter;

/**
 * @author : shujuelin
 * @date : 11:34 2021/2/9
 */

/**
 * 自定义异常
 * RuntimeException  选择性处理异常
 */
@Getter
public class DataWorkException extends RuntimeException {
    private String errorMessage;
    private int errorCode;

    public DataWorkException(String errorMessage, int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
