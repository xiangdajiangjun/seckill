package com.seckill.purchase.dao;

import com.seckill.purchase.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role,Long> {
    Role findById(Integer id);
}
