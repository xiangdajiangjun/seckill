package com.seckill.activitise.service;

import com.seckill.activitise.dao.ActivitiesDao;
import com.seckill.activitise.dao.ActivitiesShopDao;
import com.seckill.activitise.entity.Activities;
import com.seckill.activitise.entity.ActivitiesShop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivitiesService {
    @Resource
    private ActivitiesDao activitiesDao;
    @Resource
    private PurchaseRemote purchaseRemote;
    @Resource
    private ActivitiesShopDao activitiesShopDao;

    public List<Activities> findAll(){
        List<Activities> all = activitiesDao.findAll();
        return all;
    }

    /**
     * 修改活动状态
     * @param activitiesId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean continueStatus(Integer activitiesId) {
        Activities activities = activitiesDao.findById(activitiesId);
        if (activities==null)
            return false;
        Integer status = activities.getStatus();
        if (status>=1&&status<=3){
            Date startDatetime = activities.getStartDatetime();
            Date now = new Date();
            if (now.compareTo(startDatetime)>=0){
                //当前时间在开始时间之后，而状态仍然不是开启阶段，则活动取消
                activities.setStatus(-1);
                return false;
            }
            activities.setStatus(status+1);
            activitiesDao.save(activities);
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean startStatus(Integer activitiesId) {
        Activities activities = activitiesDao.findById(activitiesId);
        if (activities==null)
            return false;
        Integer status = activities.getStatus();
        if (status==4){
            Date startDatetime = activities.getStartDatetime();
            Date endDatetime = activities.getEndDatetime();
            Date now = new Date();
            if (now.compareTo(startDatetime)>=0&&now.compareTo(endDatetime)<0){
                //当前时间在区间内，改成进行状态
                activities.setStatus(5);
                activitiesDao.save(activities);
                return true;
            }
        }
        return false;
    }


    @Transactional(rollbackFor = Exception.class)
    public Boolean addActivities(Activities activities) {
        //时间处理
        Date startDatetime = activities.getStartDatetime();
        Date endDatetime = activities.getEndDatetime();
        Date nowDate = new Date();
        if (nowDate.compareTo(startDatetime)>=0||startDatetime.compareTo(endDatetime)>=0)
            return false;
        //无时间问题存入活动
        activitiesDao.save(activities);
        return true;
    }

    public Boolean joinActivities(Integer shopId, Integer activitiesId) {
        Activities activities = activitiesDao.findById(activitiesId);
        //检查商店上限是否已满
        Integer shopNum = activities.getShopNum();
        Integer shopLimit = activities.getShopLimit();
        if (shopNum>=shopLimit)
            return false;
        else
            activities.setShopNum(shopNum+1);
        //检查是否已经在该活动商家列表中
        List<Integer> shopList = activitiesShopDao.findAll().stream().map(ActivitiesShop::getShopId).collect(Collectors.toList());
        if (shopList.contains(shopId)){
            return false;
        }
        //建立活动商家映射表单
        ActivitiesShop activitiesShop =new ActivitiesShop();
        activitiesShop.setShopId(shopId);
        activitiesShop.setActivitiesId(activitiesId);
        activitiesShop.setGoodsNum(0);
        activitiesShopDao.save(activitiesShop);
        return true;
    }

    public Activities getActivitiesById(Integer activitiesId) {
        return activitiesDao.findById(activitiesId);
    }
}
