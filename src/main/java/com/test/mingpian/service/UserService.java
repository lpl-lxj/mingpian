package com.test.mingpian.service;

import com.test.mingpian.bean.User;
import com.test.mingpian.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean login(User user){//登录
        Long userid = userMapper.login(user);
        if (userid == null){//登录不成功
            return false;
        }
        else{
            return true;
        }
    }
    public void register(User user) //注册
    {
        userMapper.register(user);
    }

    public boolean checkuser(User user){ //检查用户是否存在
        Long userid = userMapper.checkuser(user.getUser_name());
        if (userid == null){//不存在可以注册
            return true;
        }
        else{   //存在不允许注册
            return false;
        }
    }

    public User getuserdata(String username)//得到用户信息
    {
        return userMapper.getuserdata(username);
    }

    public List<User> getcontactdata(Long id)//得到联系人信息
    {
        return userMapper.getcontactdata(id);
    }

    public Long getid(String username)//得到用户id
    {
        return userMapper.getid(username);
    }

    public Long updata(User user,String id)//更新用户信息
    {
        return userMapper.updata(user.getUser_xinming(),user.getUser_phone(),user.getUser_position(),user.getUser_company(),user.getUser_addr(),id);
    }
    public int del(String uid,String cid)//删除联系人信息
    {
        return userMapper.del(uid,cid);
    }
    public Long add(String uid,String cid)//添加联系人信息
    {
        return userMapper.add(uid,cid);
    }


}
