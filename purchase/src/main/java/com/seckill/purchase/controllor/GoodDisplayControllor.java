package com.seckill.purchase.controllor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodDisplayControllor {
    //@RequiresPermissions("see_new_goods")
    @RequestMapping("/newGoods")
    public String newGood(){
        return "dsffsd";
    }


    //@RequiresPermissions("into_seckill")
    @RequestMapping("/seckill")
    public String seck(){
        return "seck";
    }
}
