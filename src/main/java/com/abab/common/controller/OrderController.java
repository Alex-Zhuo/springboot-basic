package com.abab.common.controller;

import com.abab.common.entity.base.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 订单模板入口
 */
@RestController
@Api(tags = "订单")
@RequestMapping("/order")
public class OrderController {

    @GetMapping("test")
    public Result<String> test() {
        return Result.success("suafdjh");
    }
}
