package com.seckill.purchase.dao;

import com.seckill.purchase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
    User findByUsername(String userName);
}
