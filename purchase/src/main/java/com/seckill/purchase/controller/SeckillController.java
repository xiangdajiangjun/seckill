package com.seckill.purchase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @RequestMapping("/list")
    public String seck(){
        return "activitieslist";
    }
}
