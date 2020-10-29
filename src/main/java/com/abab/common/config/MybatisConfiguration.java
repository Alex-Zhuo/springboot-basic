package com.abab.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: Mybatis配置
 */
@Configuration
@MapperScan(basePackages = "com.abab.common.mapper")
public class MybatisConfiguration {

    /**
     * mybaits-plus分页支持
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
