package com.seckill.seller.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "role", schema = "db_seckill")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号
    @Basic
    @Column(name = "name")
    private String name; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    @Basic
    @Column(name = "available")
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户
    @ManyToMany(targetEntity =Permission.class,fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns ={@JoinColumn(name = "permission_id") })
    private List<Permission> permissionList;// 一个用户具有多个角色
}