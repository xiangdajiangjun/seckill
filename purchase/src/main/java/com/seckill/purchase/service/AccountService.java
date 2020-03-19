package com.seckill.purchase.service;

import com.seckill.purchase.dto.RegisterDto;
import com.seckill.purchase.entity.Account;
import org.springframework.ui.Model;

public interface AccountService {
    public Account getAccount(String username);
    public String registerAccount(RegisterDto registerDto);
    public String encryptor(Account account);
}
