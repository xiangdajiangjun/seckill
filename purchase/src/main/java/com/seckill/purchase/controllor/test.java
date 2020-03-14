package com.seckill.purchase.controllor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hollo")
public class test {
    @GetMapping("/world")
    public String test(){
        return "hollow world";
    }
}
