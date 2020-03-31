package com.seckill.purchase.dao;

import com.seckill.purchase.entity.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WindowDao extends JpaRepository<Window,Long> {
    List<Window> findAll();
}
