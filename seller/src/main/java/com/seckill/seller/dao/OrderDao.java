package com.seckill.seller.dao;

import com.seckill.seller.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order,Long> {
    List<Order> findAllByBuyerId(Integer buyerId);
    Order findById(Integer Id);
}
