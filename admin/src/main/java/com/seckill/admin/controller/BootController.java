package com.seckill.admin.controller;

import com.seckill.admin.dto.RegisterDto;
import com.seckill.admin.service.AccountService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BootController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }



    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model){
        String exception = (String) request.getAttribute("shiroLoginFailure");
        if(exception!=null){
            if (UnknownAccountException.class.getName().equals(exception)) {
                model.addAttribute("msg","账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                model.addAttribute("msg","密码不正确");
            } else if ("kaptchaValidateFailed".equals(exception)) {
                model.addAttribute("msg","验证码错误");
            } else {
                model.addAttribute("msg","账号验证失败");
            }
        }
        return "login";
    }
    @RequestMapping("/logout")
    public String logout(){
        return "welcome";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String register(RegisterDto registerDto, Model model){
        String msg = accountService.registerAccount(registerDto);
        if (msg!=null){
            if(msg.equals("success"))
                return "register-success";
            else
                model.addAttribute("msg",msg);
        }
        return "register";
    }

}
