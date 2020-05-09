package com.seckill.activitise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ActivitiesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivitiesApplication.class, args);
    }
}
