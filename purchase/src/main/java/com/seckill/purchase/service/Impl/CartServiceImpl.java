package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.CartDao;
import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.Cart;
import com.seckill.purchase.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        userDao.findByUsername(userName);
        goodDao.findById(goodId);
        Cart cart = cartDao.findByUsername(userName);
        if (cart==null)
            cart=Cart.builder().username(userName).goodList("").build();
        cart.setGoodList(cart.getGoodList()+" "+goodId);
        cartDao.save(cart);
        return true;
    }
}
