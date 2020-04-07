package com.seckill.purchase.dao;

import com.seckill.purchase.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDao extends JpaRepository<Cart,Long> {
    Cart findByUsername(String username);
}
