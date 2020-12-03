package com.abab.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author alex
 * @Date: Created in 2020/10/29 12:21
 * @IDE: Intellij Idea 2020.3
 * Description: 用户实体
 */
@Data
@TableName("`sys_user`")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "用户ID", example = "1234567898765432101")
    @TableId(value = "id", type = IdType.AUTO)
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    //出参格式化
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "状态：0.禁用；1.启用")
    private Integer status;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
