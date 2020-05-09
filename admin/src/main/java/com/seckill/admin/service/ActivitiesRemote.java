package com.seckill.admin.service;

import com.seckill.admin.entity.Activities;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= "activities")

public interface ActivitiesRemote {
    @RequestMapping("/activities/list")
    List<Activities> getAllActivities();
    @RequestMapping("/activities/continue")
    Boolean continueStatus(@RequestParam("activitiesId") Integer activitiesId);

    @RequestMapping("/activities/add")
    Boolean addActivities(@RequestBody Activities activities);
}
