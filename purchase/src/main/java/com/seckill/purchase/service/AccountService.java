package com.seckill.purchase.service;

import com.seckill.purchase.dto.RegisterDto;
import com.seckill.purchase.entity.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface AccountService {
    Account getAccount(String username);
    Boolean changeStatus(String username);
    List<Account> getAccountList();
    String registerAccount(RegisterDto registerDto);
    String encryptor(Account account);
    Map<Role, List<Permission>> getRoleAndPermission(Account account);

    List<GoodType> getGoodType();
    User getUserByUserName(String userName);
}
