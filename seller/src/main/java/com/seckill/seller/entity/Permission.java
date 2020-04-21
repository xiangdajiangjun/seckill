package com.seckill.seller.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//主键.
    @Basic
    @Column(name = "name")
    private String name;//名称.
    @Basic
    @Column(name = "resource_type")
    private String resourceType;//资源类型，[menu|button]
    @Basic
    @Column(name = "url")
    private String url;//资源路径.
    @Basic
    @Column(name = "permission")
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    @Basic
    @Column(name = "module")
    private String module;//所属模块
}
