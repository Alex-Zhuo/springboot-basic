package com.abab.common.service;

import com.abab.common.entity.User;
import com.abab.common.entity.base.Page;
import com.abab.common.entity.base.Result;
import com.abab.common.enums.ResponseCode;
import com.abab.common.mapper.UserMapper;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author alex
 * @Date: Created in 2020/10/29 14:54
 * @IDE: Intellij Idea 2020.3
 * Description:
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public User getUserById(Long userId) {
        return this.getById(userId);
    }

    public Page listUser(Page page) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getStatus, 1).last("limit " + page.getStart() + "," + page.getSize()).orderByDesc(User::getCreateTime);
        List<User> userList = this.baseMapper.selectList(wrapper);
        page.setRecords(userList);
        Integer count = this.baseMapper.selectCount(Wrappers.<User>lambdaQuery().eq(User::getStatus, 1));
        page.setTotal(count);
        return page;
    }

    public Result login(String username,String password) {
        if (username == null || username == null) {
            return Result.error(ResponseCode.INVALID_PARAM);
        }
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, username);
        User u = this.baseMapper.selectOne(wrapper);
        if (u == null) {
            return Result.error(ResponseCode.USER_NOT_EXIST);
        }
        if (!u.getPassword().equals(password)) {
            return Result.error(ResponseCode.USER_PWD_ERROR);
        }
        if (u.getStatus() == 0) {
            return Result.error(ResponseCode.USER_IS_DISABLED);
        }
        //String token = JWTUtil.generateToken(u);
        return Result.success(new JSONObject() {{
            put("id", u.getId());
            put("username", u.getUsername());
            put("roles", "admin");
            put("token", "token");
        }});
    }
}
