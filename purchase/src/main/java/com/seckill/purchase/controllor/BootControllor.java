package com.seckill.purchase.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BootControllor {

    @GetMapping("/login")
    public String login(){
        return"login";
    }

    @GetMapping("/403")
    public String lost(){
        return"403";
    }
}
