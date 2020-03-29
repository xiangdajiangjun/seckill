package com.seckill.purchase.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/good")
public class GoodControllor {
    @RequestMapping("/new")
    public String newGood(){
        return "dsffsd";
    }


    @RequestMapping("/list")
    public String goodList(){
        return "goodlist";
    }
}
