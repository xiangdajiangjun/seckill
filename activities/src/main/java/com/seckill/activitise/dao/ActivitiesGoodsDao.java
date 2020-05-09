package com.seckill.activitise.dao;

import com.seckill.activitise.entity.ActivitiesGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ActivitiesGoodsDao extends JpaRepository<ActivitiesGoods,Long> {
    List<ActivitiesGoods> findAllByActivitiesId(Integer activitiesId);
}
