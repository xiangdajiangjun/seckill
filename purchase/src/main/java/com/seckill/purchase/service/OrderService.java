package com.seckill.purchase.service;

import com.seckill.purchase.vo.OrderVo;

import java.util.List;

public interface OrderService {
    List<OrderVo> getAllOrder(String username);
    Boolean operateOderStatus(Integer OrderId);
}
