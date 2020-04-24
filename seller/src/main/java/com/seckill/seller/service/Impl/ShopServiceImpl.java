package com.seckill.seller.service.Impl;

import com.seckill.seller.dao.ShopDao;
import com.seckill.seller.entity.Shop;
import com.seckill.seller.service.ShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("shopService")
public class ShopServiceImpl implements ShopService {
    @Resource
    private ShopDao shopDao;

    @Override
    public Shop getShopByUserId(Integer userId) {
        return shopDao.findByKeepperId(userId);
    }
}
