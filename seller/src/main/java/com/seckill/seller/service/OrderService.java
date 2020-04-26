package com.seckill.seller.service;

import com.seckill.seller.entity.Order;
import com.seckill.seller.entity.Shop;
import com.seckill.seller.vo.OrderVo;

import java.util.List;

public interface OrderService {

    List<OrderVo> getOrderListByKeeperName(String keeperName);
    Boolean changeStatus(String keeperName,Integer orderId);
}
