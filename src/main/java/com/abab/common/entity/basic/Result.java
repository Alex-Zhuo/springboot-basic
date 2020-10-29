package com.abab.common.entity.basic;

import com.abab.common.enums.ResponseCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 返回数据格式封装
 */
@Data
@ApiModel("统一封装返回数据格式")
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    @ApiModelProperty("错误码")
    private Integer code;


    @ApiModelProperty("错误信息")
    private String message;


    @ApiModelProperty("返回数据")
    private Object data;

    public static Result<Void> error(String message) {
        return new Result(ResponseCode.SERVER_EXCEPTION.getCode(), message, null);
    }

    public static Result<Void> error(Integer code, String message) {
        return new Result(code, message, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success() {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    @JsonIgnore
    public boolean isFailed() {
        return !ResponseCode.SUCCESS.getCode().equals(this.code);
    }

    @JsonIgnore
    public boolean isSucceed() {
        return ResponseCode.SUCCESS.getCode().equals(this.code);
    }
}
