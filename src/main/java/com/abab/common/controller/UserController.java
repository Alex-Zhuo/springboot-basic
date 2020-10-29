package com.abab.common.controller;

import com.abab.common.entity.basic.Page;
import com.abab.common.entity.basic.Result;
import com.abab.common.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 用户模块入口
 */
@RestController
@Api(tags = "用户")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserById")
    @ApiOperation("根据用户ID获取用户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", paramType = "query")
    })
    public Result exception(Long userId) {
        return Result.success(userService.getById(userId));
    }


    @PostMapping("/listUser")
    @ApiOperation("获取所有用户")
    public Result listUser(@Valid @RequestBody Page page) {
        return Result.success(userService.listUser(page));
    }
}
