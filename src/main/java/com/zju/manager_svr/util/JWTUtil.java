package com.zju.manager_svr.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTUtil {
    private static String KEY = "zhaohan-manager";
    private static final String ISS = "zhaohan";

    private static final long HOUR = 60 * 60 * 1000;
    private static final long DAY = 24 * HOUR;

    public static String generate_access_token(int user_id) {
        return generate_access_token(user_id, Algorithm.HMAC256(KEY), 2);
    }

    public static String generate_access_token(int user_id, Algorithm algorithm, int exp) {
        Date now = new Date();
        // exp is hour
        Date expTime = new Date(now.getTime() + exp * HOUR);
        Map<String, Object> claims = new HashMap<>();
        claims.put("exp", expTime); // exp time
        claims.put("flag", 0); // 标识是否为一次性token，0是，1不是
        claims.put("iat", now);
        claims.put("iss", ISS); // 签名
        claims.put("user_id", user_id);
        return JWT.create().withPayload(claims).sign(algorithm);
    }

    public static String generate_refresh_token(int user_id) {
        return generate_refresh_token(user_id, Algorithm.HMAC256(KEY), 30);
    }

    public static String generate_refresh_token(int user_id, Algorithm algorithm, int fresh) {
        Date now = new Date();
        Date expTime = new Date(now.getTime() + fresh * DAY);
        Map<String, Object> claims = new HashMap<>();
        claims.put("exp", expTime); // exp time
        claims.put("flag", 1); // 标识是否为一次性token，0是，1不是
        claims.put("iat", now);
        claims.put("iss", ISS); // 签名
        claims.put("user_id", user_id);

        return JWT.create().withPayload(claims).sign(algorithm);
    }

    public static Map<String, Claim> decode_auth_token(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISS).build(); // Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (JWTVerificationException ex) {
            log.error("jwt decode error:", ex);
            return null;
        }
    }

    public static Integer identify(String token) {
        Map<String, Claim> payload = decode_auth_token(token);
        if (null == payload) {
            return null;
        }
        if (payload.containsKey("user_id") && payload.containsKey("flag")) {
            Integer flag = payload.get("flag").asInt();
            // access token
            if (0 == flag) {
                return payload.get("user_id").asInt();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
