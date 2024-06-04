package com.insta.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JWTUtil {
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    private static final String SECRET = "token_secret";


    public static String sign(String username, Integer userId) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withClaim("username", username)
                .withClaim("userId", userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }


}
