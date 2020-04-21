package com.seckill.seller.service;

import com.seckill.seller.dto.RegisterDto;
import com.seckill.seller.entity.*;

import java.util.List;
import java.util.Map;

public interface AccountService {
    Account getAccount(String username);
    String registerAccount(RegisterDto registerDto);
    String encryptor(Account account);
    Map<Role, List<Permission>> getRoleAndPermission(Account account);

    List<GoodType> getGoodType();
    User getUserByUserName(String userName);
}
