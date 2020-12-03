package com.abab.common.service;

import com.abab.common.config.SysConfig;
import com.abab.common.entity.User;
import com.abab.common.entity.basic.Page;
import com.abab.common.entity.basic.Result;
import com.abab.common.enums.ResponseCode;
import com.abab.common.exception.BusinessException;
import com.abab.common.mapper.UserMapper;
import com.abab.common.util.JwtUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author alex
 * @Date: Created in 2020/10/29 14:54
 * @IDE: Intellij Idea 2020.3
 * Description:
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    JwtUtil jwtUtil;

    @Resource
    SysConfig sysConfig;

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

    public Result login(String phone, String code) throws BusinessException {
        LambdaQueryWrapper<User> userWrapper = Wrappers.lambdaQuery();
        userWrapper.eq(User::getPhone, phone);
        userWrapper.last("limit 1");
        User user = this.baseMapper.selectOne(userWrapper);
        if (user == null) {
            throw new BusinessException(ResponseCode.USER_NOT_EXIST);
        }
        if (user.getStatus() == 0) {
            throw new BusinessException(ResponseCode.USER_IS_DISABLED);
        }
        String token = jwtUtil.generateToken(user);
        return Result.success(new JSONObject() {{
            put(sysConfig.getHeaderToken(), token);
            put("userInfo", user);
        }});
    }
}
