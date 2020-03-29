package com.seckill.purchase.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "shop", schema = "db_seckill", catalog = "")
public class Shop {
    private int id;
    private String name;
    private Integer shopLevel;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "shop_level")
    public Integer getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(Integer shopLevel) {
        this.shopLevel = shopLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop that = (Shop) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(shopLevel, that.shopLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shopLevel);
    }
}
