package com.seckill.purchase.dao;

import com.seckill.purchase.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order,Long> {
    List<Order> findAllByBuyerId(Integer buyerId);
}
