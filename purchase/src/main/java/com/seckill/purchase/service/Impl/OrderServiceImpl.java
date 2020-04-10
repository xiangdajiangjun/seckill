package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.OrderDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.Order;
import com.seckill.purchase.service.OrderService;
import com.seckill.purchase.vo.OrderVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private UserDao userDao;
    @Resource
    private OrderDao orderDao;
    @Override
    public List<OrderVo> getAllOrder(String username) {
        List<OrderVo> orderVoList = new ArrayList<>();

        Integer userId = userDao.findByUsername(username).getId();
        List<Order> orderList = orderDao.findAllByBuyerId(userId);
        orderList.forEach(order -> {
            OrderVo orderVo = OrderVo.builder().orderId(order.getId()).goodName(order.getGoodsName()).price(order.getPrice())
                    .count(order.getGoodsNum()).status(order.getStatus()).priceTotal(order.getPrice()*order.getGoodsNum())
                    .build();
            orderVoList.add(orderVo);
        });
        return orderVoList;
    }
}
