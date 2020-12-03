package com.abab.common.util;

import com.abab.common.config.SysConfig;
import com.abab.common.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author alex
 * @Date: Created in 2020/12/3 9:28
 * @IDE: Intellij Idea 2020.3
 * Description:
 */
@Component
public class JwtUtil {

    @Resource
    private SysConfig sysConfig;

    public String generateToken(User user) {
        return JWT
                .create()
                .withExpiresAt(getExpirationDate())
                .withAudience(user.getId().toString())
                .sign(Algorithm.HMAC256(user.getPhone()));
    }

    public Long getUserIdByToken(String token) {
        return Long.valueOf(JWT.decode(token).getAudience().get(0));
    }

    public String getTokenByRequest(HttpServletRequest request) {
        return request.getHeader(sysConfig.getHeaderToken());
    }

    public Long getUserIdByRequest(HttpServletRequest request) {
        String token = getTokenByRequest(request);
        return getUserIdByToken(token);
    }

    public boolean validateToken(User user, String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPhone())).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return true;
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + sysConfig.getTokenExpiration());
    }

    public Long getUserId(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return getUserIdByRequest(request);
    }
}
