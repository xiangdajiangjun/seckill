package com.seckill.activitise.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= "purchase")
public interface PurchaseRemote {

    @RequestMapping(value = "/api/goods/del")
    Integer getShopIdByUsername(String username);




}
