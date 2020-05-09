package com.seckill.activitise.dao;

import com.seckill.activitise.entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ActivitiesDao extends JpaRepository<Activities,Long> {
    Activities findById(Integer activitiesId);
}
