package com.seckill.seller.dao;

import com.seckill.seller.entity.GoodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodTypeDao extends JpaRepository<GoodType,Long> {
    List<GoodType> findAll();
}
