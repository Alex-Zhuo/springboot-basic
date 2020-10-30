package com.abab.common.enums;

import com.abab.common.entity.basic.Result;
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

    SUCCESS(Integer.valueOf(0), "成功"),
    SERVER_EXCEPTION(Integer.valueOf(-1), "服务器异常"),
    ILLEGAL_USER(Integer.valueOf(1001), "用户不合法"),
    DATA_NOT_EXIST(Integer.valueOf(1002), "数据不存在"),
    PERMISSION_DENIED(Integer.valueOf(1003), "无访问权限"),
    OPERATION_FAILED(Integer.valueOf(1004), "操作失败"),
    INCOMPLETE_PARAMETERS(Integer.valueOf(1005), "参数不全"),
    INVALID_PARAMETER(Integer.valueOf(1006), "参数不合法"),

    INVALID_PARAM(400, "参数错误"),
    UNAUTHORIZED(401, "请求资源未授权"),
    EXPIRED(402, "登录状态已失效,请重新登录"),
    //UNAUTHORIZED(403, "请求资源未授权"),
    SERVER_ERR(500, "服务器异常"),
    PASSWORD_ERR(501, "密码错误"),
    ACCOUNT_DISABLED(502, "账号已停用"),
    NO_PERMISSION(503, "无权限，请找管理员授权");

    private Integer code;
    private String message;

    @SneakyThrows
    public String toJSON() {
        return new ObjectMapper().writeValueAsString(Result.error(this.code, this.message));
    }
}
