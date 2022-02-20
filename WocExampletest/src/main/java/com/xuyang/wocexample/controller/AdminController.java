package com.xuyang.wocexample.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.xuyang.wocexample.bean.Result;
import com.xuyang.wocexample.bean.User;
import com.xuyang.wocexample.service.UserService;
import com.xuyang.wocexample.utils.JWTutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class AdminController {
    @Autowired
    private UserService userService;
    /**
     * 获取用户数量
     */
    @GetMapping(value = "/getusercount")
    public Result regist(HttpServletRequest request){
        //获取payload信息
        Result result = getResult(request);
        if (result != null) return result;
        return userService.getuserCount();


    }
    /**
     * 删除用户账号
     */
    @PostMapping(value = "/delete")
    public Result login(HttpServletRequest request,User user){
        //获取payload信息
        Result result = getResult(request);
        if (result != null) return result;
        return userService.delete(user);
    }

    private Result getResult(HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT verify = JWTutils.verifyToken(token);
        if(verify.getClaim("role").isNull()) {
            Result result = new Result();
            result.setMsg("无权限！");
            result.setSuccess(false);
            return result;
        }
        return null;
    }
}
