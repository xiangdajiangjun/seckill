package com.seckill.seller.service.Impl;

import com.seckill.seller.entity.Activities;
import com.seckill.seller.entity.ActivitiesGoods;
import com.seckill.seller.service.ActivitiesRemote;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivitiesService {
    @Resource
    private ActivitiesRemote activitiesRemote;

    public List getActivitiesList(Integer status) {
        List<Activities> allActivities = activitiesRemote.getAllActivities();
        if (status!=0){
            allActivities=allActivities.stream().filter(activities -> activities.getStatus()==status).collect(Collectors.toList());
        }
        return allActivities;
    }

    public Boolean joinActivities(Integer shopId, Integer activitiesId) {
        return activitiesRemote.joinActivities(shopId,activitiesId);
    }

    /**
     * 得到某活动的上架商品列表
     * @param activitiesId
     * @return
     */
    public List<ActivitiesGoods> getActivitiesGoodsList(Integer activitiesId) {
        return activitiesRemote.getActivitiesGoodsList(activitiesId);
    }

    public Activities getActivitiesById(Integer activitiesId) {
        return activitiesRemote.getActivitiesById(activitiesId);
    }

    /**
     * 用于添加上架一件商品，缺少商品名和原价字段，没必要补充进数据库，在买家读取的时候添加上去就行了。
     * @param activitiesGoods
     * @return
     */
    public Boolean saveActivitiesGoods(ActivitiesGoods activitiesGoods) {
        return activitiesRemote.saveActivitiesGoods(activitiesGoods);
    }
}
