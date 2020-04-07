package com.seckill.purchase.controller;

import com.seckill.purchase.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/info")
    public String getUserInfo(Model model){
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("userInfo",accountService.getUserByUserName(userName));
        return "userinfo";
    }
}
