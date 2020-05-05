package com.seckill.purchase.api;

import com.seckill.purchase.entity.Account;
import com.seckill.purchase.entity.GoodType;
import com.seckill.purchase.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api/account")
public class AccountApi {
    @Resource
    private AccountService accountService;

    @RequestMapping("/status")
    @ResponseBody
    Boolean changeAccountStatus(@RequestParam("username") String username){
        Boolean aBoolean = accountService.changeStatus(username);
        return aBoolean;
    }

    @RequestMapping(value = "/byname")
    @ResponseBody
    public Account getAccountByUsername(@RequestParam("username") String username){
        return accountService.getAccount(username);
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Account> getAccountList(){
        return accountService.getAccountList();
    }
}
