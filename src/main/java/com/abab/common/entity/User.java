package com.abab.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author alex
 * @Date: Created in 2020/10/29 12:21
 * @IDE: Intellij Idea 2020.3
 * Description: 用户实体
 */
@Data
public class User {

    @JsonFormat(shape= JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "用户ID",example = "1234567898765432101")
    private Long id;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "创建时间")
    //入参格式化 @DateTimeFormat(pattern = "yyyy-MM-dd")
    //出参格式化
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    //出参格式化
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "状态：0.禁用；1.启用")
    private Integer status;
}
