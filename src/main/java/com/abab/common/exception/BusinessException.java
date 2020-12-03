package com.abab.common.exception;

import com.abab.common.enums.ResponseCode;
import lombok.Getter;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 业务异常配置
 */
@Getter
public class BusinessException extends BaseException {

    public static BusinessException DATE_TRANSFORM_ERROR = new BusinessException(1004, "日期转化错误");

    private final Integer code;
    private final String msg;

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ResponseCode response) {
        this(response.getCode(), response.getMessage());
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = -1;
        this.msg = msg;
    }
}
