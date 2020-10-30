package com.abab.common.entity;

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
    private Long id;
    private String username;
    private String phone;
    private String password;
    private Date createTime;
    private Date updateTime;
    private Integer status;
}
