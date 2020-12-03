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
     * <p>
     * 模块定义：1->用户模块；2->产品模块；3->订单模块；4->配置模块；5->文件模块；6->微信相关模块
     */

    SUCCESS(0, "成功"),
    FAIL(-1, "失败"),

    SERVER_EXCEPTION(10000, "服务端发生未知异常"),
    DATA_NOT_EXIST(10001, "数据不存在"),
    PERMISSION_DENIED(1003, "无访问权限"),
    OPERATION_FAILED(1004, "操作失败"),

    INVALID_PARAM(40000, "参数错误"),
    INCOMPLETE_PARAMETERS(40001, "参数不全"),
    INVALID_PARAMETER(40002, "参数不合法"),

    //用户模块501
    UN_AUTHENTICATION(50100, "用户未认证,请先登录"),
    EXPIRED(50101, "登录状态已失效,请重新登录"),
    INVALID_TOKEN(50102, "无效的accessToken"),
    USER_NOT_EXIST(50103, "用户不存在"),
    USER_EXIST(50104, "用户已存在，该手机号已注册"),
    USER_IS_DISABLED(50105, "用户已被禁用"),
    USER_PWD_ERROR(50106, "密码错误"),
    NO_PERMISSION(50107, "没有操作权限"),


    //产品模块502
    PRODUCT_NOT_EXIST(50201, "产品不存在"),
    PRODUCT_NAME_EXIST(50202, "产品名称已存在"),
    PRODUCT_NUMBER_ERROR(50203, "最大值不能小于最小值"),

    //订单模块503
    EXIST_MESSAGE(50301, "已收到您的佣金结算申请，平台核实放款数据后马上进行转账。各银行到账时间不一致，请留意手机到账信息。"),

    //配置相关模块504

    //文件模块505
    FILE_NOT_EXIST(50501, "文件不存在"),
    FILE_UPLOAD_FAILED(50502, "文件上传失败"),

    //微信模块506
    MESSAGE_INVALID_PARAM(50601, "推送失败,参数异常");

    private Integer code;
    private String message;

    @SneakyThrows
    public String toJSON() {
        return new ObjectMapper().writeValueAsString(Result.error(this.code, this.message));
    }
}
