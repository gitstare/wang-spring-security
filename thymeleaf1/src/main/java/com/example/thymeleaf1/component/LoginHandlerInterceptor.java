package com.example.thymeleaf1.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//登陆检查
//拦截器进行登录检查防止没有输入密码直接用URL登录页面
public class LoginHandlerInterceptor implements HandlerInterceptor {
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //从session中获取loginuser的值
        Object user = httpServletRequest.getSession().getAttribute("loginUser");
        if(user==null){
            //未登录返回登录页面

            httpServletRequest.setAttribute("msg","没有权限请先登陆");
            httpServletRequest.getRequestDispatcher("/index.html").forward(httpServletRequest,httpServletResponse);
            //httpServletRequest.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }else{
            //
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
