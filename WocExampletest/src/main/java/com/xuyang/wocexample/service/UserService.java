package com.xuyang.wocexample.service;

import com.xuyang.wocexample.bean.Result;
import com.xuyang.wocexample.mapper.UserMapper;
import com.xuyang.wocexample.bean.User;
import com.xuyang.wocexample.utils.JWTutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

//Service层：建立在DAO层之上，Controller层之下。调用Dao层的接口（Mapper.java），为Controller层提供接口。
// 负责业务模块的逻辑应用设计，首先设计接口，再设计其实现的类。
@Service
@Transactional(rollbackFor = RuntimeException.class)//异常处理相关注解
public class UserService{
    @Autowired
    private UserMapper userMapper;
    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    public Result regist(User user) {
        Result result = new Result();
        //初始化设置
        result.setSuccess(false);
        result.setDetail(null);
        //try catch？
        try {
            //以用户名获取数据库中的User信息
            //findUserByName返回User对象
            User existUser = userMapper.findUserByName(user.getUsername());
            if(existUser != null){
                //如果用户名已存在，设置返回信息
                result.setMsg("用户名已存在");

            }else{
                //注册用户
                //regist为void方法
                user.setRole(null);
                userMapper.regist(user);
                //System.out.println(user.getId());
                //设置返回信息
                result.setMsg("注册成功");
                result.setSuccess(true);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        //最后返回信息
        return result;
    }/**
     * 登录
     * @param user 用户名和密码
     * @return Result
     */
    public Result login(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            //login执行成功返回非0值
            User userDB = userMapper.login(user);
            if(userDB.getId()== null){
                result.setMsg("用户名或密码错误");
            }else{
                //获取token令牌(其中包含用户名和角色)
                HashMap<String, String> payload = new HashMap<>();
                payload.put("username",userDB.getUsername());
                payload.put("role",userDB.getRole());
//                System.out.println(userDB.getRole());
                //生成JWT令牌
                String token = JWTutils.getToken(payload);
                //设置结果
                result.setMsg("登录成功");
                result.setSuccess(true);
                result.setToken(token);//返回token令牌
                result.setDetail(userDB);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    //查询账号数量
    public Result getuserCount() {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            result.setMsg("用户数量查询成功");
            result.setDetail("用户的数量为"+userMapper.getuserCount());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    //删除某个账号
    public Result delete(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User existUser = userMapper.findUserByName(user.getUsername());
            if(existUser == null){
                //如果用户名已存在，设置返回信息
                result.setMsg("用户名未存在");
                result.setSuccess(false);
            }
            else {
                userMapper.delete(user);
                result.setMsg("用户删除成功");
                result.setSuccess(true);
            }

        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}