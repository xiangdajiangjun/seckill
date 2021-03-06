package com.seckill.seller.service;

import com.seckill.seller.entity.Activities;
import com.seckill.seller.entity.ActivitiesGoods;
import com.seckill.seller.entity.ActivitiesShop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= "activities")

public interface ActivitiesRemote {
    @RequestMapping("/activities/list")
    List<Activities> getAllActivities();
    @RequestMapping("/activities/join")
    Boolean joinActivities(@RequestParam("shopId") Integer shopId,@RequestParam("activitiesId") Integer activitiesId);
    @RequestMapping("/activities_goods/list")
    List<ActivitiesGoods> getActivitiesGoodsList(@RequestParam("activitiesId") Integer activitiesId);

    @RequestMapping("/activities/get")
    Activities getActivitiesById(@RequestParam("activitiesId") Integer activitiesId);

    @RequestMapping("/activities_shop/list")
    List<ActivitiesShop> getActivitiesShopListByActivitiesId(@RequestParam("activitiesId") Integer activitiesId);

    @RequestMapping("/activities_shop/get")
    ActivitiesShop getActivitiesShop(@RequestParam("activitiesId") Integer activitiesId,@RequestParam("shopId")  Integer shopId);

    @RequestMapping("/activities_goods/add")
    Boolean saveActivitiesGoods(@RequestBody ActivitiesGoods activitiesGoods);
}
