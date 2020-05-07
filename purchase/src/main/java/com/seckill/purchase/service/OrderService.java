package com.seckill.purchase.service;

import com.seckill.purchase.vo.OrderVo;

import java.util.List;

public interface OrderService {
    List<OrderVo> getAllOrder(String username);
    Boolean operateOderStatus(Integer OrderId);
    List<OrderVo> getOrderByShopId(Integer shopId);

    Boolean returnGoods(Integer orderId);

    Boolean agreeApply(Integer orderId);

    Boolean refuseApply(Integer orderId);
}
