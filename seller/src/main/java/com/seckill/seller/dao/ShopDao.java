package com.seckill.seller.dao;

import com.seckill.seller.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface ShopDao extends JpaRepository<Shop,Long> {
    Shop findByKeepperId(Integer userId);
}
