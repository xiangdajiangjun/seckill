package com.seckill.activitise.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "activities_goods", schema = "db_seckill", catalog = "")
public class ActivitiesGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;//商品名
    private Integer shopId;
    private Integer activitiesId;
    private Integer goodsId;
    private Double originalPrice;
    private Double presentPrice;//单价
    private Integer allLimit;
    private Integer singleLimit;
    private String buyerList;//购买了此商品的用户id列表
    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;
}
