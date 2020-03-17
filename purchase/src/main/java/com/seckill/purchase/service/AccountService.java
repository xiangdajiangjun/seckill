package com.seckill.purchase.service;

import com.seckill.purchase.entity.Account;

public interface AccountService {
    public Account findByUsername(String username);
}
