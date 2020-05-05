package com.seckill.admin.service.Impl;

import com.seckill.admin.entity.*;
import com.seckill.admin.dto.RegisterDto;

import com.seckill.admin.service.AccountService;
import com.seckill.admin.service.PurchaseRemote;
import feign.FeignException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.exceptions.Exceptions;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private PurchaseRemote purchaseRemote;

    @Override
    public Boolean changeStatus(String username) {
        Boolean isSuccess = purchaseRemote.changeAccountStatus(username);
        return isSuccess;
    }

    @Override
    public List<Account> getAccountList() {
        List<Account> accountList = purchaseRemote.getAccountList();
        return accountList;
    }

    @Override
    public Account getAccount(String username) {
        Account account;
        try {
            account = purchaseRemote.getAccountByUsername(username);
        }
        catch (FeignException fei){
            String message = fei.getMessage();
            throw new UnknownAccountException(message);
        }
        if (account==null||(!account.getType().equals("3")))
            throw new UnknownAccountException();
        return account;
    }


}
