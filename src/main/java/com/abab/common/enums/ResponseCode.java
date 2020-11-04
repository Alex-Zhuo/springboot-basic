package com.abab.common.enums;

import com.abab.common.entity.base.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 基础返回码和返回信息定义
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    /**
     * 错误码暂定都是5位数字，并配有相应解释
     * 错误码按模块按功能场景分级分段，前三位错误码表示模块，第四位表示模块下的功能
     * 数字 1 开头的错误码表示系统级别的错误，比如缺少某种字符集，连不上数据库之类的，系统级的错误码不需要分模块，可以按照自增方式进行添加
     * 数字 4 开头的错误码表示API参数校验失败，比如 “交易模块下单场景中，订单金额参数不能为空” 可以用 40111 错误码来表示
     * 数字 5 开头的错误码表示后台业务校验失败，比如 “交易模块下单场景中，该用户没有下单权限” 可以用 50111 错误码来表示
     *
     * 模块定义：1->用户模块
     */

    SUCCESS(0, "成功"),

    SERVER_EXCEPTION(10000, "服务器异常"),
    DATA_NOT_EXIST(10001, "数据不存在"),
    PERMISSION_DENIED(1003, "无访问权限"),
    OPERATION_FAILED(1004, "操作失败"),
    
    INVALID_PARAM(40000, "参数错误"),
    INCOMPLETE_PARAMETERS(40001, "参数不全"),
    INVALID_PARAMETER(40002, "参数不合法"),

    EXPIRED(50000, "登录状态已失效,请重新登录"),
    USER_NOT_EXIST(50101, "用户不存在"),
    USER_PWD_ERROR(50102, "密码错误"),
    NO_PERMISSION(50103, "没有操作权限"),
    USER_IS_DISABLED(50104, "用户已被禁用");

    private Integer code;
    private String message;

    @SneakyThrows
    public String toJSON() {
        return new ObjectMapper().writeValueAsString(Result.error(this.code, this.message));
    }
}
