package com.seckill.purchase.dao;

import com.seckill.purchase.entity.GoodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodTypeDao extends JpaRepository<GoodType,Long> {
    GoodType findById(Integer typeId);
    void deleteById(Integer typeId);
    List<GoodType> findAll();
}
