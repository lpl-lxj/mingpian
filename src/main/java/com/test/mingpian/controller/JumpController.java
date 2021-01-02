package com.test.mingpian.controller;

import com.test.mingpian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class JumpController {//跳转控制类
    @Autowired
    private UserService userService;

    @RequestMapping("/login")//跳转到登录页面
    public String login(){
        return "login";
    }
    @RequestMapping("/register")//跳转到注册页面
    public String register(){
        return "register";
    }

    @RequestMapping("/main")//跳转到主页面
    public String user(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断是否已经登录没有登陆就跳转到登录页面
        ServletContext application = request.getSession().getServletContext();
        if(application.getAttribute("user") == null)
        {
            return "redirect:/login";//返回登陆界面
        }
        else//已经登录,显示相应数据
        {
            //放入application便于前端页面进行访问
            application.setAttribute("userdata", userService.getuserdata(application.getAttribute("user").toString()));//放入的是用户数据
            model.addAttribute("contactdata",userService.getcontactdata(userService.getid(application.getAttribute("user").toString())));//放入联系人数据
            application.setAttribute("id",userService.getid(application.getAttribute("user").toString()).toString());//用户id
            return "main";//返回用户主界面
        }
    }

}
