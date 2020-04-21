package com.seckill.seller.dao;

import com.seckill.seller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
    User findByUsername(String userName);
}
