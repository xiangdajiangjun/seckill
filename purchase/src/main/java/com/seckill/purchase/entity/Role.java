package com.seckill.purchase.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role", schema = "db_seckill")
public class Role {
    @Id
    @GeneratedValue
    private Integer id; // 编号
    @Basic
    @Column(name = "name")
    private String name; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    @Basic
    @Column(name = "available")
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户
}