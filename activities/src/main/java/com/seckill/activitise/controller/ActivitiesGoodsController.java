package com.seckill.activitise.controller;

import com.seckill.activitise.dao.ActivitiesGoodsDao;
import com.seckill.activitise.dao.ActivitiesShopDao;
import com.seckill.activitise.entity.Activities;
import com.seckill.activitise.entity.ActivitiesGoods;
import com.seckill.activitise.entity.ActivitiesShop;
import com.seckill.activitise.service.ActivitiesService;
import com.seckill.activitise.service.ActivitiesShopService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/activities_goods")
public class ActivitiesGoodsController {
    @Resource
    private ActivitiesGoodsDao activitiesGoodsDao;
    @Resource
    private ActivitiesShopService activitiesShopService;
    @Resource
    private ActivitiesShopDao activitiesShopDao;

    @RequestMapping("/list")
    @ResponseBody
    public List<ActivitiesGoods> seeActivitiesGoods(@RequestParam("activitiesId") Integer activitiesId){
        List<ActivitiesGoods> ActivitiesGoodsList = activitiesGoodsDao.findAllByActivitiesId(activitiesId);
        return ActivitiesGoodsList;
    }

    @RequestMapping("/add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    Boolean saveActivitiesGoods(@RequestBody ActivitiesGoods activitiesGoods){
        Integer shopId = activitiesGoods.getShopId();
        ActivitiesShop activitiesShop = activitiesShopService.getActivitiesShop(activitiesGoods.getActivitiesId(), shopId);
        activitiesShop.setGoodsNum(activitiesShop.getGoodsNum()+1);
        activitiesShopDao.save(activitiesShop);
        activitiesGoodsDao.save(activitiesGoods);
        return true;
    }

}
