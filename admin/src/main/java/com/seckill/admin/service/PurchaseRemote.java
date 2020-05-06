package com.seckill.admin.service;

import com.seckill.admin.dto.RegisterDto;
import com.seckill.admin.entity.Account;
import com.seckill.admin.entity.GoodType;
import com.seckill.admin.entity.Role;
import com.seckill.admin.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= "purchase")
public interface PurchaseRemote {
    @RequestMapping("/api/account/byname")
    Account getAccountByUsername(@RequestParam("username") String username);

    @RequestMapping("/api/account/list")
    List<Account> getAccountList();

    @RequestMapping("/api/account/status")
    Boolean changeAccountStatus(@RequestParam("username") String username);

    @RequestMapping(value = "/api/goods/type")
    List<GoodType> getGoodsType();
    @RequestMapping("/api/goods/type/del")
    Boolean delType(@RequestParam("typeId") Integer typeId);
    @RequestMapping("/api/goods/type/changestatus")
    Boolean changeGoodsTypeStatus(@RequestParam("typeId") Integer typeId);
    @RequestMapping(value = "/api/goods/type/add")
    Boolean addGoodsType(@RequestParam("tag") String tag);

    @RequestMapping(value = "/api/account/register")
    String registerAccountForAdmin(RegisterDto registerDto);

    @RequestMapping(value = "/api/role/list")
    List<Role> getRoleList();

    @RequestMapping(value = "/api/role/distribute")
    Boolean roleDistribute(@RequestParam("username") String username,@RequestParam("roleId") Integer roleId);
}
