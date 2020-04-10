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
    @Override
    public Boolean addGood(String userName, Integer goodId) {
        Goods goods = goodDao.findById(goodId);
        if(goods==null)
            return false;
        else if(!goods.getIsSell())
            return false;
        Cart cart = cartDao.findByUsername(userName);
        if (cart==null)
            cart=Cart.builder().username(userName).goodList("").build();
        cart.setGoodList(cart.getGoodList()+" "+goodId);
        cartDao.save(cart);
        return true;
    }

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

    @Override
    public Boolean deleteGoods(String userName,Integer goodId) {
        try{
            //查询本用户购物车
            Cart cart = cartDao.findByUsername(userName);
            cart.setGoodList(cart.getGoodList().replaceFirst(goodId+"",""));
            cartDao.save(cart);
        }catch(Exception e){
            return false;
        }
        return true;
    }

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
