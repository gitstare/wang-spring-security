package com.example.thymeleaf1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;
//map存放错误消息
//session 获取登录信息username
@Controller
public class LoginController {
    @RequestMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String passeord,
                        Map<String,Object> map, HttpSession session){
        if (!StringUtils.isEmpty(username)&& "123456".equals(passeord)){

            //登陆成功后，防止表单重复提交，可以重定向到主页
            //session.setAttribute("loginUser");
            //session.setAttribute("loginUser");
            session.setAttribute("loginUser", username);
            //
            return "redirect:/main.html";
        }else{
            //登陆失败后
            map.put("msg","用户名错误");
            return "login";
        }
    }
}
