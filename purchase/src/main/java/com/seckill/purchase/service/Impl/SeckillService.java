package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.OrderDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.ActivitiesGoods;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.service.ActivitiesRemote;
import com.seckill.purchase.service.GoodService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SeckillService {
    @Resource
    private GoodService goodService;
    @Resource
    private ActivitiesRemote activitiesRemote;
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserDao userDao;
    @Resource
    private GoodDao goodDao;

    @Cacheable(cacheNames = "activitiesGoods",key = "#activitiesGoodsId")
    public ActivitiesGoods getActivitiesGoods(Integer activitiesGoodsId){
        return activitiesRemote.getActivitiesGoods(activitiesGoodsId);
    }
    @CachePut(cacheNames = "activitiesGoods",key = "#activitiesGoods.id")
    public ActivitiesGoods saveActivitiesGoods(ActivitiesGoods activitiesGoods) throws Exception {
        Boolean isSuccess = activitiesRemote.saveActivitiesGoods(activitiesGoods);
        if (isSuccess)
            return activitiesGoods;
        else {
            throw new Exception("保存秒杀商品失败");
        }
    }

    @Cacheable(cacheNames = "goods",key = "#goodsId")
    public Goods findGoodsById(Integer goodsId){
        return goodDao.findById(goodsId);
    }

    @Transactional(rollbackFor = Exception.class)
    @CachePut(cacheNames = "goods",key = "#goods.id")
    public Goods saveGoods (Goods goods){
        goodDao.save(goods);
        return goods;
    }
}
