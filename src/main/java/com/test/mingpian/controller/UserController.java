package com.test.mingpian.controller;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.test.mingpian.QRcodeUtil;
import com.test.mingpian.bean.User;
import com.test.mingpian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/addpeople",method= RequestMethod.POST)//扫码添加联系人
    @ResponseBody
    public void addpeople(@RequestParam("img") MultipartFile img, HttpServletRequest request, HttpServletResponse response) throws NotFoundException, IOException {
        //二维码文件处理
        ServletContext application = request.getSession().getServletContext();
        FileInputStream in = (FileInputStream) img.getInputStream();
        BufferedImage image= javax.imageio.ImageIO.read(in);
        BinaryBitmap bb=new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        HashMap map =new HashMap();
        map.put(DecodeHintType.CHARACTER_SET, "utf-8");
        Result result = new MultiFormatReader().decode(bb,map);
        //添加联系人
        userService.add(userService.getid(application.getAttribute("user").toString()).toString(),result.getText());
        response.sendRedirect("/main");
    }
    //生成二维码
    @RequestMapping("/QR")
    public void getQRcode(HttpServletResponse response,String id) {
        // 二维码的内容
        String content = id;
        //参数为二维码的内容、长、宽、响应对象
        QRcodeUtil.creatRrCode(content,250,250,response);
    }
    //编辑数据
    @RequestMapping("/updata")
    public String update(HttpServletRequest request,User user) {
        ServletContext application = request.getSession().getServletContext();
        userService.updata(user,userService.getid(application.getAttribute("user").toString()).toString());
        return "redirect:/main";
    }
    //删除数据
    @RequestMapping("/del")
    public String update(HttpServletRequest request,String u,String c) {
        userService.del(userService.getid(u).toString(),userService.getid(c).toString());
        return "redirect:/main";
    }
}
