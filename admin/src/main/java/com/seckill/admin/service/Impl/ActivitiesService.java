package com.seckill.admin.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import com.seckill.admin.entity.Activities;
import com.seckill.admin.service.ActivitiesRemote;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    public Boolean continueStatus(Integer activitiesId) {
        return activitiesRemote.continueStatus(activitiesId);
    }

    public Boolean addActivities(Activities activities) {
        return activitiesRemote.addActivities(activities);
    }
}
