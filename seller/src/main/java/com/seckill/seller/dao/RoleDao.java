package com.seckill.seller.dao;

import com.seckill.seller.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role,Long> {
    public Role findById(Integer id);
}
