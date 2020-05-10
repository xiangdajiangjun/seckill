package com.seckill.purchase.service.Impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TestService {


    @Cacheable(value = "test",key = "#test_s")
    public String getRandom(Integer test_s){
        String s = UUID.randomUUID().toString();
        System.out.println(s);
        return s;
    }
}
