package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linziyu on 2018/5/13.
 * 视图分发类
 *
 */

@Controller
public class WebController {
    private final String PREFIX = "pages/";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @RequestMapping("/")
    public String index(Model model) {

        return "/index";
    }
    @RequestMapping("/level1/{path}")
    public String level1(@PathVariable("path") String path) {
        return PREFIX+"level1/"+path;
    }
    @RequestMapping("/level2/{path}")
    public String level2(@PathVariable("path")String path) {
        return PREFIX+"level2/"+path;
    }

    @RequestMapping("/toAddUser")
    public String toAddUser() {
            return "/addUser";
    }

    @RequestMapping("/addUser")
    public String addUser(String name,String password,String role) {
        //String encodePassword = MD5Util.encode(password);
       // User user = new User(name,encodePassword);
        User user = new User(name,password);
        List<Role> roles = new ArrayList<>();
        Role role1 = roleRepository.findByRolename(role);
        roles.add(role1);
        user.setRoles(roles);
        userRepository.save(user);
            return "/index";
    }

}
