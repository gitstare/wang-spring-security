package com.example.thymeleaf1.controller;

import com.example.thymeleaf1.dao.DepartmentDao;
import com.example.thymeleaf1.dao.EmployeeDao;
import com.example.thymeleaf1.entities.Department;
import com.example.thymeleaf1.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    //查寻所有员工返回列表页面

    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees = employeeDao.getAll();
        //放在请求域中
        model.addAttribute("emps",employees);

        return "emp/list";
    }
    //来到员工添加页面
    @GetMapping("/emp")
    public String toAddPage(Model model){
        //来到添加页面,查出所有的部门
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }
    //员工添加
    //springmvc自动将请求参数和入惨的对象一一绑定，要求请求参数的名字和javabean 的对象里面的名字属性一样
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        //System.out.println("保存员工信息"+employee);
        employeeDao.save(employee);

        //来到员工列表页面
        //redirect:表示重定向到一个地址
        //forward:表示转发一个地址
        return  "redirect:/emps";
    }
    //来到修改页面，查出当前员工，在页面会显示
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);
        //页面要显示所有的部分
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);

        //回到修改页面
        return "emp/add";

    }
    //员工修改
    @PutMapping("/emp")
    public String updateEmployee(Employee employee){
        System.out.println("修改员工"+employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }
    //员工删除
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }

}
