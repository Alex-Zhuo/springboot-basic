package com.abab.common.service;

import com.abab.common.entity.User;
import com.abab.common.entity.basic.Page;
import com.abab.common.mapper.UserMapper;
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
        /*wrapper.apply("1 = 1");
        wrapper.apply("phone_num not like '22%'");*/
        wrapper.last("limit " + page.getStart() + "," + page.getSize()).orderByDesc(User::getCreateTime);
        List<User> userList = this.list(wrapper);
        page.setRecords(userList);
        List<User> list = this.list();
        page.setTotal(list == null ? 0 : list.size());
        return page;
    }
}
