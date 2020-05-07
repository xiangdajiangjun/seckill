package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.CartDao;
import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.OrderDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.Cart;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.entity.Order;
import com.seckill.purchase.service.CartService;
import com.seckill.purchase.vo.CartItem;
import com.seckill.purchase.vo.CartVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("cartService")
public class CartServiceImpl implements CartService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserDao userDao;
    @Resource
    private GoodDao goodDao;
    @Resource
    private CartDao cartDao;

    /**
     * 加入购物车
     * @param userName 用户名
     * @param goodId 商品id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addGood(String userName, Integer goodId) {
        //检查是否存在商品，或者商品是否售卖，或者商品库存是否不足
        Goods goods = goodDao.findById(goodId);
        if(goods==null)
            return false;
        else if(!goods.getIsSell())
            return false;
        else if (goods.getStock()<=0)
            return false;
        //将该商品加入购物车：若不存在该用户的购物车则创建；在购物车商品列表末尾添加该商品的id，商品库存减一。
        Cart cart = cartDao.findByUsername(userName);
        if (cart==null)
            cart=Cart.builder().username(userName).goodList("").build();
        cart.setGoodList(cart.getGoodList()+" "+goodId);
        cartDao.save(cart);
        //商品库存变化
        goods.setStock(goods.getStock()-1);
        goodDao.save(goods);
        return true;
    }

    /**
     * 通过账户名得到所有的购物车内项目
     * @param userName
     * @return
     */
    @Override
    public CartVo seeCart(String userName) {
        //查询本用户购物车
        Cart cart = cartDao.findByUsername(userName);
        if (cart==null)
            return null;
        //分割商品id的字符串，处理后得到  商品id-该商品数量
        Map<Integer, Long> mapGoodNumber = Arrays.stream(cart.getGoodList().split("\\s+")).filter(s -> !s.isEmpty())
                .map(Integer::valueOf).collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        //根据根据商品id查询商品列表,并根据商品id映射
        Map<Integer, Goods> goodMap = goodDao.findByIdIn(mapGoodNumber.keySet()).stream().collect(Collectors.toMap(Goods::getId, good -> good, (k1, k2) -> k1));
        //封装成条目列表
        List<CartItem> cartItemList =new ArrayList<>();
        for (Integer goodId:mapGoodNumber.keySet()){
            //商品
            Goods goods = goodMap.get(goodId);
            //商品数量
            Long count = mapGoodNumber.get(goodId);
            //商品总价
            Double total = count*goods.getPrice();
            //封装
            CartItem cartItem = CartItem.builder().goodId(goods.getId()).goodName(goods.getName()).isSell(goods.getIsSell()).count(count).price(goods.getPrice()).priceTotal(total).build();
            cartItemList.add(cartItem);
        }
        //封装成购物车清单
        double sum = cartItemList.stream().filter(CartItem::getIsSell).mapToDouble(CartItem::getPriceTotal).sum();
        return CartVo.builder().cartListVoList(cartItemList).firstPrice(sum).discount(0).priceTotal(sum).build();
    }

    /**
     * 摘出购物车
     * @param userName
     * @param goodId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteGoods(String userName,Integer goodId) {
        try{
            //查询本用户购物车
            Cart cart = cartDao.findByUsername(userName);
            cart.setGoodList(cart.getGoodList().replaceFirst(goodId+"",""));
            cartDao.save(cart);
        }catch(Exception e){
            return false;
        }
        //库存加一
        Goods goods = goodDao.findById(goodId);
        if (goods!=null){
            //若商品已经被删除，不需要操作
            goods.setStock(goods.getStock()+1);
            goodDao.save(goods);
        }
        return true;
    }

    /**
     * 支付，先检查库存，下单直接支付成功，形成订单，最后清空购物车
     * @param userName
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean payForCart(String userName) {
        List<CartItem> cartItemList = seeCart(userName).getCartListVoList().stream().filter(CartItem::getIsSell).collect(Collectors.toList());
        int userId = userDao.findByUsername(userName).getId();
        List<Order> orderList = new ArrayList<>();
        cartItemList.forEach(cartItem -> {
            Order order = Order.builder().orderTime(Timestamp.valueOf(LocalDateTime.now())).price(cartItem.getPrice()).buyerId(userId).goodsId(cartItem.getGoodId()).goodsName(cartItem.getGoodName()).goodsNum(cartItem.getCount()).status(1).build();
            orderList.add(order);
        });
        orderDao.saveAll(orderList);
        cartDao.deleteByUsername(userName);
        return true;
    }
}
