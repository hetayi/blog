package com.may.blog.utils;

import com.may.blog.dao.pojo.SysUser;
import com.may.blog.service.SysUserService;
import com.may.blog.vo.ErrorCode;
import com.may.blog.vo.Result;
import com.may.blog.vo.params.LoginParam;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static LoginParam loginParam;
    @Autowired
    private static SysUserService sysUserService;

    private static final String jwtToken = "123456Mszlu!@#$$";

    public static String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
//        String token = JWTUtils.createToken(100L);
//        System.out.println(token);
//        Map<String, Object> map = JWTUtils.checkToken(token);
//        System.out.println(map.get("userId"));


        final String slat = "may!@#";

        String account = "admin";
        String password = "admin";

        password = DigestUtils.md5Hex(password + slat);

        SysUser sysUser = sysUserService.findUser(account,password);

        String token = JWTUtils.createToken(sysUser.getId());
        System.out.println(token);
    }

}
