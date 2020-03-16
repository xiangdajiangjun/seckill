package com.seckill.purchase.controllor;

import com.seckill.purchase.dao.AccountDao;
import com.seckill.purchase.dao.RoleDao;
import com.seckill.purchase.entity.Account;
import com.seckill.purchase.entity.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/za")
public class test {
    @Resource
    private AccountDao accountDao;
    @Resource
    private RoleDao roleDao;
    @GetMapping("/waluduo")
    public String test(){
        Account account = accountDao.findAccountByUsername("xiang");
        Role relo = roleDao.findById(1);
        account.getRoleList().add(relo);
        //accountDao.save(account);
        return account.getRoleList().toString();
    }
}
