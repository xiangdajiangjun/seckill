package com.seckill.purchase.dao;

import com.seckill.purchase.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GoodDao extends JpaRepository<Goods,Long> {
    List<Goods> findByIdIn(Collection<Integer> idList);
    Goods findById(Integer id);
}
