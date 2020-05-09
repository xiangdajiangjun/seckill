package com.seckill.activitise.controller;

import com.seckill.activitise.entity.ActivitiesShop;
import com.seckill.activitise.service.ActivitiesShopService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/activities_shop")
public class ActivitiesShopController {
    @Resource
    private ActivitiesShopService activitiesShopService;

    @RequestMapping("/list")
    @ResponseBody
    List<ActivitiesShop> getActivitiesShopListByActivitiesId(@RequestParam("activitiesId") Integer activitiesId){
        return activitiesShopService.getActivitiesShopListByActivitiesId(activitiesId);
    }

    @RequestMapping("/get")
    @ResponseBody
    ActivitiesShop getActivitiesShop(@RequestParam("activitiesId") Integer activitiesId,@RequestParam("shopId")  Integer shopId){
        return activitiesShopService.getActivitiesShop(activitiesId,shopId);
    }

}
