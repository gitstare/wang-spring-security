package com.example.thymeleaf1.component;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

//给容器中加入我们自己的定义的Errorattribute
@Component
//给容器中加入自己定义的的Erroratturate
public class MyErrorAttributes extends DefaultErrorAttributes {
    //返回值得map就是页面和json能获取的所有字段
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(requestAttributes, includeStackTrace);
        map.put("company","atguigu");
        //我们的异常处理器携带的数据

        Map<String,Object> ext = (Map<String, Object>) requestAttributes.getAttribute("ext", 0);
        return map;
    }
}