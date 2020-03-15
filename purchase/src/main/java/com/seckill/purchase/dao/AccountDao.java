package com.seckill.purchase.dao;

import com.seckill.purchase.entity.Account;
import com.seckill.purchase.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AccountDao extends JpaRepository<Account,Long> {
    Account findAccountByUsername(String username);
}
