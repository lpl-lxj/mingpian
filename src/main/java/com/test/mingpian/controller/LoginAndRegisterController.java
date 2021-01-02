package com.test.mingpian.controller;

import com.test.mingpian.bean.User;
import com.test.mingpian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginAndRegisterController {
    @Autowired
    private UserService userService;
    @PostMapping("/login_ing")//登录验证
    public String login(Model model, HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        boolean tag = userService.login(user);
        if (tag) {//登录成功
            //放入application
            ServletContext application = request.getSession().getServletContext();
            application.setAttribute("user", user.getUser_name());
            return "redirect:/main";//重定向到主界面
        } else {//登录失败
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('账号密码错误');</script>");
            return "/login";
        }
    }

    @RequestMapping("/register_ing")//注册验证
    public String register(HttpServletRequest request, HttpServletResponse response,User user) throws IOException {
        boolean tag = userService.checkuser(user);
        if (tag)
        {//不存在可以注册
            userService.register(user);
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('注册成功');</script>");
            return "redirect:/login";
        }
        else//已存在不允许注册
        {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('用户已存在');</script>");
            return "/register";
        }
    }
    @RequestMapping("/exit")//退出登录
    public String exit(HttpServletRequest request,Model model){
        ServletContext application = request.getSession().getServletContext();
        application.removeAttribute("user");
        return "redirect:/login";
    }
}
