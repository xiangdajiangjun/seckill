package com.seckill.seller.dao;

import com.seckill.seller.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GoodDao extends JpaRepository<Goods,Long> {
    List<Goods> findAll();
    List<Goods> findByIdIn(Collection<Integer> idList);
    Goods findById(Integer id);
}
