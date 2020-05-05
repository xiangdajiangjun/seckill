package com.seckill.admin.service;

import com.seckill.admin.entity.Account;
import com.seckill.admin.entity.GoodType;
import com.seckill.admin.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= "purchase")
public interface PurchaseRemote {
    @RequestMapping(value = "/api/goods/type")
    List<GoodType> getGoodsType();

    @RequestMapping("/api/account/byname")
    Account getAccountByUsername(@RequestParam("username") String username);

    @RequestMapping("/api/account/list")
    List<Account> getAccountList();

    @RequestMapping("/api/account/status")
    Boolean changeAccountStatus(@RequestParam("username") String username);



}
