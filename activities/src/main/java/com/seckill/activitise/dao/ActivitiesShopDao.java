package com.seckill.activitise.dao;

import com.seckill.activitise.entity.Activities;
import com.seckill.activitise.entity.ActivitiesShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ActivitiesShopDao  extends JpaRepository<ActivitiesShop,Long> {
    List<ActivitiesShop> findAllByActivitiesId(Integer activitiesId);
    ActivitiesShop findByActivitiesIdAndShopId(Integer activitiesId, Integer shopId);
}
