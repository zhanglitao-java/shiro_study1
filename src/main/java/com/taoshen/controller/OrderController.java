package com.taoshen.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("order")
public class OrderController {
    @RequestMapping("save")
    //@RequiresRoles("user")//具有user角色
    @RequiresRoles(value={"admin","user"})//同时具有两个角色才行

    @RequiresPermissions("user:update:01")//具有该访问资源权限
    public String save(){
        System.out.println("涛神年薪二十万");
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //代码方式
        if(subject.hasRole("admin")){
            System.out.println("保存订单");
        }else{
            System.out.println("无权访问");
        }
        return "redirect:/index.jsp";
    }
}
