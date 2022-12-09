package com.university.demo.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.university.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: 15760
 * @Date: 2020/3/24
 * @Descripe: Token下发
 */

@Service
public class TokenService {

    /**
     * 验证管理员
     * @param user
     * @return
     */
    public String getToken(User user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 500;
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getId().toString()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
