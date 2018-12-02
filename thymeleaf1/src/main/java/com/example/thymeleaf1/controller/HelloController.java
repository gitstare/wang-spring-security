package com.example.thymeleaf1.controller;

import com.example.thymeleaf1.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {


//    @RequestMapping("/")
//    public String index(){
//        return "index";
//    }


    @RequestMapping("/success")
    public String success(){
        return "success";
    }

//    @RequestMapping ("/success")
//    public String hello(Map<String,Object> map){
//
//        map.put("hello","<h1>你好</h1>");
//
//        return "success";
//    }
    @ResponseBody
    @RequestMapping("/hello")
    public  String hello(@RequestParam("user") String user){
        if(user.equals("aaa")){
            throw new UserNotExistException();
        }
        return "hello word";
    }
}
