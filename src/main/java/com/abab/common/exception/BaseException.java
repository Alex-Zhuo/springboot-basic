package com.abab.common.exception;

import com.abab.common.entity.base.Result;
import com.abab.common.enums.ResponseCode;
import lombok.Getter;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 基本异常配置
 */
@Getter
public class BaseException extends Exception {

    private Integer code;
    private String message;

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public static Result exceptionResponse(ResponseCode response) {
        return Result.error(response.getCode(), response.getMessage());
    }
}
