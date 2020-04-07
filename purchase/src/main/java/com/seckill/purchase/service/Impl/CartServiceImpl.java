package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.CartDao;
import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.Cart;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.service.CartService;
import com.seckill.purchase.vo.CartItem;
import com.seckill.purchase.vo.CartVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("cartService")
public class CartServiceImpl implements CartService {
    @Resource
    private UserDao userDao;
    @Resource
    private GoodDao goodDao;
    @Resource
    private CartDao cartDao;
    @Override
    public Boolean addGood(String userName, Integer goodId) {
        goodDao.findById(goodId);
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
        //分割商品id的字符串，处理后得到商品id-该商品数量的map
        Map<Integer, Long> mapGoodNumber = Arrays.stream(cart.getGoodList().split(" "))
                .map(Integer::valueOf).collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        //根据根据商品id查询商品列表,并根据商品id映射
        Map<Integer, Goods> goodMap = goodDao.findByIdIn(mapGoodNumber.keySet()).stream().collect(Collectors.toMap(Goods::getId, good -> good, (k1, k2) -> k1));
        //封装成条目列表(中断一下重构哈)
        for (Integer goodId:mapGoodNumber.keySet()){
            Goods good = goodMap.get(goodId);
//            CartItem cartItem = CartItem.builder().goodName(good.getName()).count(mapGoodNumber.get(goodId)).price();
        }
        return null;
    }
}
