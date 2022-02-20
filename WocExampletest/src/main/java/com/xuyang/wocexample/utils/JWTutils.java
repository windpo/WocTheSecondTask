package com.xuyang.wocexample.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

//JWT生成器和验证器
public class JWTutils {
    private static final String SING="WA(*WQ&FQW*&(G*&^A*&G^*&A^SDD*&ASF";
    /**
     * 生成token header.payload.sing
     */
    public static String getToken(Map<String,String> map){//map为传入token中payload的信息
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7);//默认七天过期

        //创建JWTbuilder
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        //创建过期时间
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SING));//设置签名 密钥保密
        return token;
    }
    /**
     * 验证token合法性
     */
    public static DecodedJWT verifyToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
    }
    /**
     * 获取token信息
     */
    public static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
    }
}
