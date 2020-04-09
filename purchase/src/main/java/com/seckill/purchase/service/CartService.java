package com.seckill.purchase.service;

import com.seckill.purchase.vo.CartVo;

public interface CartService {
    Boolean addGood(String userName,Integer goodId);
    CartVo seeCart(String userName);
    Boolean deleteGoods(String userName,Integer goodId);
}
