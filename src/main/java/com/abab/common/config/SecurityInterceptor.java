package com.abab.common.config;

import com.abab.common.enums.ResponseCode;
import com.abab.common.exception.BusinessException;
import com.abab.common.util.CiphertextUtil;
import com.abab.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author alex
 * @Date: Created in 2021/1/13 9:44
 * @IDE: Intellij Idea 2020.3
 * Description:
 * 1. 重放攻击
 * 2. api参数防篡改
 */
@Slf4j
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    public static SecurityInterceptor interceptor;

    @Resource
    private SysConfig sysConfig;

    @Resource
    private RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        interceptor = this;
        interceptor.sysConfig = this.sysConfig;
        interceptor.redisUtils = this.redisUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isFromSwagger(request)) {
            return true;
        }
        // 获取时间戳
        String timestamp = request.getHeader("timestamp");
        // 获取随机字符串
        String nonceStr = request.getHeader("nonceStr");
        // 获取签名
        String signature = request.getHeader("signature");

        Date expiredDate = new Date(Long.parseLong(timestamp) + interceptor.sysConfig.getApiTimeout() * 1000);
        if (StringUtils.isEmpty(timestamp) || new Date().after(expiredDate)) {
            log.info("get resource from {} throw an exception:{}", request.getRequestURL(), ResponseCode.INVALID_TIMESTAMP.getMessage());
            throw new BusinessException(ResponseCode.INVALID_TIMESTAMP);
        }

        if (StringUtils.isEmpty(nonceStr) || interceptor.redisUtils.exists(nonceStr)) {
            log.info("get resource from {} throw an exception:{}", request.getRequestURL(), ResponseCode.INVALID_NONCE.getMessage());
            throw new BusinessException(ResponseCode.INVALID_NONCE);
        }

        String sign = signature(interceptor.sysConfig.getSignToken(), timestamp, nonceStr);
        if (StringUtils.isEmpty(signature) || (!signature.equals(sign))) {
            log.info("get resource from {} throw an exception:{}", request.getRequestURL(), ResponseCode.INVALID_SIGNATURE.getMessage());
            throw new BusinessException(ResponseCode.INVALID_SIGNATURE);
        }

        interceptor.redisUtils.setWithSeconds(nonceStr, nonceStr, Long.valueOf(interceptor.sysConfig.getApiTimeout()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private boolean isFromSwagger(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        //TODO:set accessToken Header
        return referer != null && referer.contains("swagger-ui.html");
    }

    public static String signature(String token, String timestamp, String nonce) {
        //生成签名
        List<String> params = new ArrayList<String>(5) {{
            add(token);
            add(timestamp);
            add(nonce);
        }};
        //1. 将token、timestamp、nonce三个参数进行字典序排序
        params.sort(Comparator.naturalOrder());
        //2. 将三个参数字符串拼接成一个字符串进行sha1加密
        String signStr = params.get(0) + params.get(1) + params.get(2);
        return CiphertextUtil.passAlgorithmsCiphering(signStr, CiphertextUtil.SHA_1);
    }
}
