package com.seckill.activitise.service;

import com.seckill.activitise.dao.ActivitiesShopDao;
import com.seckill.activitise.entity.ActivitiesShop;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActivitiesShopService {
    @Resource
    private ActivitiesShopDao activitiesShopDao;
    public List<ActivitiesShop> getActivitiesShopListByActivitiesId(Integer activitiesId) {
        return activitiesShopDao.findAllByActivitiesId(activitiesId);
    }

    public ActivitiesShop getActivitiesShop(Integer activitiesId, Integer shopId) {
        return activitiesShopDao.findByActivitiesIdAndShopId(activitiesId,shopId);
    }
}
