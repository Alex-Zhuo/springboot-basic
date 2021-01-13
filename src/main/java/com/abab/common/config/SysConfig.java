package com.abab.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author alex
 * @Date: Updated in 2020/12/03 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 读取配置文件
 */
@Configuration
@Data
public class SysConfig {

    @Value("${header.token}")
    private String headerToken;

    @Value("${token.expiration}")
    private Long tokenExpiration;

    @Value("${sign.token}")
    private String signToken;

    @Value("${api.timeout}")
    private Integer apiTimeout;
}
