package com.seckill.seller.service.Impl;

import com.seckill.seller.entity.ActivitiesShop;
import com.seckill.seller.service.ActivitiesRemote;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service

public class ActivitiesShopService {
    @Resource
    private ActivitiesRemote activitiesRemote;

    public List<ActivitiesShop> getActivitiesShopListByActivitiesId(Integer activitiesId) {
        return activitiesRemote.getActivitiesShopListByActivitiesId(activitiesId);
    }

    public ActivitiesShop getActivitiesShop(Integer activitiesId, Integer shopId) {
        return activitiesRemote.getActivitiesShop(activitiesId,shopId);
    }
}
