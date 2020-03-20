package com.seckill.purchase.service;

import com.seckill.purchase.dto.RegisterDto;
import com.seckill.purchase.entity.Account;
import com.seckill.purchase.entity.Permission;
import com.seckill.purchase.entity.Role;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface AccountService {
    public Account getAccount(String username);
    public String registerAccount(RegisterDto registerDto);
    public String encryptor(Account account);
    public Map<Role, List<Permission>> getRoleAndPermission(Account account);
}
