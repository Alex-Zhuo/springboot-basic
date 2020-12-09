package com.abab.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: Swagger配置
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "uat"})
public class SwaggerConfiguration {

    @Bean
    public Docket createUserRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.abab.common.controller"))
                .paths(PathSelectors.regex("/user/.*"))
                .build();
    }

    @Bean
    public Docket createOrderRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("订单")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.abab.common.controller"))
                .paths(PathSelectors.regex("/order/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot项目基础框架")
                .description("springboot项目脚手架")
                .version("1.0")
                .contact(new Contact("alex", "url", "123@idwarf.cn"))
                .build();
    }
}
