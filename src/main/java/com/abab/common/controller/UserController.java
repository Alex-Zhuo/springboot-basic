package com.abab.common.controller;

import com.abab.common.entity.User;
import com.abab.common.entity.base.Page;
import com.abab.common.entity.base.Result;
import com.abab.common.exception.BusinessException;
import com.abab.common.service.UserService;
import io.swagger.annotations.*;
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
    public Result<User> getUserById(Long userId) {
        return Result.success(userService.getUserById(userId));
    }


    @PostMapping("/listUser")
    @ApiOperation("获取所有用户")
    public Result<Page<User>> listUser(@Valid @RequestBody Page page) {
        return Result.success(userService.listUser(page));
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", dataType = "String", paramType = "query", required = true, example = "15297746757"),
            @ApiImplicitParam(name = "code", value = "短信验证码", dataType = "String", paramType = "query", required = true, example = "1234")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功，data=>accessToken中为此次请求的token"),
            @ApiResponse(code = -1, message = "业务校验不通过，详情取返回数据中message的值"),
            @ApiResponse(code = 40000, message = "参数错误，详情取返回数据中message的值"),
            @ApiResponse(code = 50103, message = "用户手机号未注册"),
            @ApiResponse(code = 50105, message = "用户账户已被禁用"),
    })
    public Result login(@RequestParam(value = "phone") String phone, @RequestParam(value = "code") String code) throws BusinessException {
        return userService.login(phone, code);
    }
}
