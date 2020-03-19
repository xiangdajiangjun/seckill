package com.seckill.purchase.controllor;

import com.seckill.purchase.dto.RegisterDto;
import com.seckill.purchase.entity.Account;
import com.seckill.purchase.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class BootControllor {

    @Autowired
    private AccountService accountService;
    @GetMapping("/login")
    public String login(){
//        SecurityUtils.getSubject().getSession().setTimeout(0);
        return"login";
    }

    //@ResponseBody
    @PostMapping("/login")
    public void login(HttpServletRequest request, Model model){
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
        //return"403";
    }

    @GetMapping("/403")
    public String lost(){
        return"403";
    }

    @GetMapping("/register")
    public String signup(){
        return "register";
    }
    @PostMapping("/register")
    public String signup(RegisterDto registerDto, Model model){
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
