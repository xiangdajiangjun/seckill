package com.seckill.seller.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @Field("goods_id")
    private int id;
    private String name;//商品名
    private String code;//商品码
    private Integer shopId;//店铺id
    private Double price;//单价
    private String stock;//库存
    private String describe;//描述
    private Integer salesVolume;//销量（总）
    private Boolean isSell;
    private Integer typeId;
    private Timestamp createDate;
    private Timestamp updateDate;
    @Field("image_url")
    private String image;
}