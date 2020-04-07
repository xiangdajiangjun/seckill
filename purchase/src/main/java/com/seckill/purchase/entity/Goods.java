package com.seckill.purchase.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@Data
@Entity
@Table(name = "goods", schema = "db_seckill")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;//商品名
    private String code;//商品码
    private Long shopId;//店铺id
    private Double price;//单价
    private String stock;//库存
    @Column(name = "describe")
    private String describe;//描述
    private Integer salesVolume;//销量（总）

    @Column(name = "is_sell")
    private Boolean isSell;
    private Timestamp createDate;
    private Timestamp updateDate;
    @Column(name = "image_url")
    private String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods that = (Goods) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(code, that.code) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(isSell, that.isSell) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, shopId, isSell, createDate, updateDate);
    }
}
