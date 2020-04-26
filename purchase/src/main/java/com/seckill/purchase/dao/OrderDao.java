package com.seckill.purchase.dao;

import com.seckill.purchase.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order,Long> {
    List<Order> findAllByBuyerId(Integer buyerId);
    Order findById(Integer Id);
    List<Order> findAllByGoodsIdIn(List<Integer> goodsIdList);
    void deleteById(Integer id);
}
