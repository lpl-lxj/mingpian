package com.test.mingpian.mapper;

import com.test.mingpian.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    //登录
    @Select("select u.id from user u where u.user_name=#{user_name} and u.user_pwd=#{user_pwd}")
    Long login(User user);
    //注册
    @Insert("Insert into user(user_name,user_pwd) values(#{user_name},#{user_pwd})")
    Long register(User user);
    //检查用户名是否存在
    @Select(value = "select u.id from user u where u.user_name=#{username}")
    Long checkuser(@Param("username") String username);
    //得到用户数据
    @Select(value = "select * from user u where u.user_name=#{username}")
    User getuserdata(@Param("username") String username);
    //得到用户id
    @Select(value = "select id from user u where u.user_name=#{username}")
    Long getid(@Param("username") String username);
    //得到联系人信息
    @Select(value = "Select a.user_name,a.user_xinming,a.user_phone,a.user_position,user_company,a.user_addr FROM `user` a JOIN contacts b ON a.id=b.contacts_id WHERE b.user_id=#{id}")
    List<User> getcontactdata(@Param("id") Long id);
    //修改自己信息
    @Update(value = "Update user set user_xinming=#{xinming},user_phone=#{phone},user_position=#{position},user_company=#{company},user_addr=#{addr} where id=#{id}")
    Long updata(@Param("xinming") String xinming,@Param("phone") String phone,@Param("position") String position,@Param("company") String company,@Param("addr") String addr,@Param("id") String id);
    //删除联系人
    @Delete("Delete from contacts where user_id=#{uid} AND contacts_id=#{cid}")
    int del(@Param("uid") String uid,@Param("cid") String cid);
    //绑定联系人关系
    @Insert("Insert into contacts(user_id,contacts_id) values(#{uid},#{cid})")
    Long add(@Param("uid") String uid,@Param("cid") String cid);
}
