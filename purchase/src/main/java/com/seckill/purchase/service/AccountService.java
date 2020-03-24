package com.seckill.purchase.service;

import com.seckill.purchase.dto.RegisterDto;
import com.seckill.purchase.entity.Account;
import com.seckill.purchase.entity.GoodType;
import com.seckill.purchase.entity.Permission;
import com.seckill.purchase.entity.Role;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface AccountService {
    Account getAccount(String username);
    String registerAccount(RegisterDto registerDto);
    String encryptor(Account account);
    Map<Role, List<Permission>> getRoleAndPermission(Account account);

    List<GoodType> getGoodType();
}
