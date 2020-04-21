package com.seckill.seller.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "goods", schema = "db_seckill")
public class Goods {
    @Field("goods_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Field("name")
    private String name;//商品名
    private String code;//商品码
    private Long shopId;//店铺id
    @Field("price")
    private Double price;//单价
    private String stock;//库存
    @Field("describe")
    private String describe;//描述
    private Integer salesVolume;//销量（总）

    @Column(name = "is_sell")
    private Boolean isSell;
    private Integer typeId;
    private Timestamp createDate;
    private Timestamp updateDate;
    @Field("image_url")
    @Column(name = "image_url")
    private String image;
}