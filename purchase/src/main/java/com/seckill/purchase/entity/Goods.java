package com.seckill.purchase.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
@Data
@Entity
@Table(name = "goods", schema = "db_seckill")
public class Goods implements Serializable {
    @Field("goods_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Field("name")
    private String name;//商品名
    private String code;//商品码
    private Integer shopId;//店铺id
    @Field("price")
    private Double price;//单价
    private Integer stock;//库存
    @Field("goods_describe")
    @Column(name = "goods_describe")
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