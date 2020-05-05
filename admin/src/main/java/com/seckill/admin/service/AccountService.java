package com.seckill.admin.service;

import com.seckill.admin.dto.RegisterDto;
import com.seckill.admin.entity.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface AccountService {
    Account getAccount(String username);
    List<Account> getAccountList();
    Boolean changeStatus(String username);
//    String registerAccount(RegisterDto registerDto);
//    String encryptor(Account account);
//    Map<Role, List<Permission>> getRoleAndPermission(Account account);
//
//    List<GoodType> getGoodType();
//    User getUserByUserName(String userName);
}
