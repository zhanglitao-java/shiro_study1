package com.taoshen.controller;

import com.taoshen.entity.User;
import com.taoshen.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用来处理用户登录信息完成身份认证
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
    public String login(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        //通过shiro提供的SecurityUtils工具类调用函数获取subject主体
        Subject subject = SecurityUtils.getSubject();
        //搞出一个token 作为subject调用login的形参
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //开始对用户的token进行认证
            subject.login(token);
            //如果这里没有出现异常就会执行到这里 也就会重定向到index.jsp页面
            return "redirect:/index.jsp";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        } finally {
            System.out.println("微笑着面对他");
        }
        //如果出现了异常了 就不会走上面的return 走完finally之后 就会走到这里的return 说明登录失败 重定向到login.jsp登录页面
        return "redirect:/login.jsp";
    }

    /**
     * 当前用户退出
     * @return
     */
    @RequestMapping("logout")
    public String logout(){
        //依然是通过工具类获取当前的subject主体
        Subject subject = SecurityUtils.getSubject();
        //调用提供的logout函数 就会跳出登录
        subject.logout();
        //用户跳出登录之后  业务上当然还是重定向到登录页面
        return "redirect:/login.jsp";
    }

    /**
     * 用户注册
     *
     */
    @RequestMapping("register")
    //从前端获取到请求参数 注册的用户名和明文密码 封装进了User中对应的属性
    public String register(User user){
        try {
            //开始走Service层  如果没有出现异常 就说明注册成功 就直接跳转到登录页面
            userService.register(user);
            return "redirect:/login.jsp";
        } catch (Exception e) {
            //如果出现异常了 就说明注册失败 依然是跳转到注册页面
            e.printStackTrace();
            return "redirect:/register.jsp";
        }
    }

}
