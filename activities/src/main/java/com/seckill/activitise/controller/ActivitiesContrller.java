package com.seckill.activitise.controller;

import com.seckill.activitise.entity.Activities;
import com.seckill.activitise.service.ActivitiesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/activities")
public class ActivitiesContrller {

    @Resource
    private ActivitiesService activitiesService;

    @RequestMapping("/save")
    @ResponseBody
    Boolean saveActivities(@RequestBody Activities activities){
        return activitiesService.saveActivities(activities);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Activities> seeActivities(){
        List<Activities> activitiesList = activitiesService.findAll();
        return activitiesList;
    }

    @RequestMapping("/continue")
    @ResponseBody
    Boolean continueStatus(@RequestParam("activitiesId") Integer activitiesId){
        return activitiesService.continueStatus(activitiesId);
    }

    @RequestMapping("/add")
    @ResponseBody
    Boolean addActivities(@RequestBody Activities activities){
        return activitiesService.addActivities(activities);
    }

    @RequestMapping("/join")
    @ResponseBody
    Boolean joinActivities(@RequestParam("shopId") Integer shopId,@RequestParam("activitiesId") Integer activitiesId){
        return activitiesService.joinActivities(shopId,activitiesId);
    }

    @RequestMapping("/get")
    @ResponseBody
    Activities getActivitiesById(@RequestParam("activitiesId") Integer activitiesId){
        return activitiesService.getActivitiesById(activitiesId);
    }
}
