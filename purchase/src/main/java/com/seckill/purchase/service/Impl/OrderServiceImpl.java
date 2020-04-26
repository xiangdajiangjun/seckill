package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.OrderDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.entity.Order;
import com.seckill.purchase.service.GoodService;
import com.seckill.purchase.service.OrderService;
import com.seckill.purchase.vo.OrderVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private GoodService goodService;
    @Resource
    private GoodDao goodDao;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean operateOderStatus(Integer orderId) {
        Order order = orderDao.findById(orderId);
        if (order==null)
            return false;
        if (order.getStatus()>=4){
            orderDao.deleteById(orderId);
            return true;
        }
        order.setStatus(order.getStatus()+1);
        orderDao.save(order);
        return true;
    }

    @Override
    public List<OrderVo> getOrderByShopId(Integer shopId) {
        List<Integer> goodsIdList = goodService.getGoodsListForSeller(shopId).stream().map(Goods::getId).collect(Collectors.toList());
        List<Order> orderList = orderDao.findAllByGoodsIdIn(goodsIdList);

        List<OrderVo> orderVoList = new ArrayList<>();
        orderList.forEach(order -> {
            OrderVo orderVo = OrderVo.builder().orderId(order.getId()).goodName(order.getGoodsName()).price(order.getPrice())
                    .count(order.getGoodsNum()).status(order.getStatus()).priceTotal(order.getPrice()*order.getGoodsNum())
                    .build();
            orderVoList.add(orderVo);
        });
        return orderVoList;
    }
}
