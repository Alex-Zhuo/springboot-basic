package com.abab.common.config;

import com.abab.common.annotation.ValidUser;
import com.abab.common.entity.User;
import com.abab.common.enums.ResponseCode;
import com.abab.common.exception.BusinessException;
import com.abab.common.service.UserService;
import com.abab.common.util.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author alex
 * @Date: Created in 2020/12/02 15:40
 * @IDE: Intellij Idea 2020.3
 * @Description: JWT拦截
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    public static AuthenticationInterceptor interceptor;

    @Resource
    private UserService userService;

    @Resource
    private SysConfig sysConfig;

    @Resource
    private JwtUtil jwtUtil;

    @PostConstruct
    public void init() {
        interceptor = this;
        interceptor.sysConfig = this.sysConfig;
        interceptor.userService = this.userService;
        interceptor.jwtUtil = this.jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws BusinessException {
        String token = httpServletRequest.getHeader(interceptor.sysConfig.getHeaderToken());
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(ValidUser.class)) {
            ValidUser validUser = method.getAnnotation(ValidUser.class);
            if (!validUser.value()) {
                return true;
            }
        }
        // 执行认证
        if (token == null) {
            throw new BusinessException(ResponseCode.UN_AUTHENTICATION);
        }
        // 获取token中的userId
        Long userId = interceptor.jwtUtil.getUserIdByToken(token);
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getStatus, 1);
        wrapper.eq(User::getId, userId);
        User user = interceptor.userService.getBaseMapper().selectOne(wrapper);
        if (user == null) {
            throw new BusinessException(ResponseCode.USER_NOT_EXIST);
        }
        if (user.getStatus() == 0) {
            throw new BusinessException(ResponseCode.USER_IS_DISABLED);
        }
        // 验证 token
        interceptor.jwtUtil.validateToken(user, token);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
    }
}