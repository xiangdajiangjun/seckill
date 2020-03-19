package com.seckill.purchase.dao;

import com.seckill.purchase.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<Account,Long> {
    Account findAccountByUsername(String username);
}
