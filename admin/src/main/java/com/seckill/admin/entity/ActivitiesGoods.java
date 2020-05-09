package com.seckill.admin.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "activities_shop", schema = "db_seckill", catalog = "")
public class ActivitiesGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer activitiesId;
    private Integer goodsId;
    private Double originalPrice;
    private Double presentPrice;//单价
    private Integer allLimit;
    private Integer singleLimit;
    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;
}
