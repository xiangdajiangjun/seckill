package com.seckill.seller.service;

import com.seckill.seller.entity.Shop;

public interface ShopService {
    Shop getShopByUserId(Integer userId);
}
