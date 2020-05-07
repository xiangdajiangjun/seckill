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


    /**
     * 退货退款
     * @param orderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean returnGoods(Integer orderId) {
        Order order = orderDao.findById(orderId);
        if (order==null)
            return false;
        if (order.getStatus()>=4){
            return false;
        }
        if (order.getStatus()==0){
            //取消订单，库存返回
            Integer goodsId = order.getGoodsId();
            Goods goods = goodDao.findById(goodsId);
            //商品删除或者下架了不用操作，否则返回库存
            if (goods!=null){
                if (goods.getIsSell()){
                    goods.setStock(goods.getStock()+order.getGoodsNum().intValue());
                    goodDao.save(goods);
                }
            }
            orderDao.deleteById(orderId);
            return true;
        }
        order.setStatus(-1*order.getStatus());
        orderDao.save(order);
        return true;
    }

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

    /**同意退货
     * 直接修改为200状态
     * @param orderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean agreeApply(Integer orderId) {
        Order order = orderDao.findById(orderId);
        if (order==null)
            return false;
        if (order.getStatus()<0){
            Goods goods = goodDao.findById(order.getGoodsId());
            if (goods!=null) {
                goods.setStock(goods.getStock()+order.getGoodsNum().intValue());
            }
            order.setStatus(200);
            orderDao.save(order);
            return true;
        }
        return false;
    }

    /**拒绝退货
     * 打回原状态
     * @param orderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean refuseApply(Integer orderId) {
        Order order = orderDao.findById(orderId);
        if (order==null)
            return false;
        if (order.getStatus()<0){
            order.setStatus(-1*order.getStatus());
            orderDao.save(order);
            return true;
        }
        return false;
    }
}
