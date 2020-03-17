package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.AccountDao;
import com.seckill.purchase.entity.Account;
import com.seckill.purchase.service.AccountService;
import com.seckill.purchase.service.exception.SginException;
import org.apache.shiro.authc.UnknownAccountException;
import org.omg.CORBA.UnknownUserException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;

    @Override
    public Account findByUsername(String username) {
        Account account = accountDao.findAccountByUsername(username);
        if (account==null)
            throw new UnknownAccountException();
        return account;
    }
}
