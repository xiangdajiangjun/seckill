package com.seckill.seller.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "shop", schema = "db_seckill", catalog = "")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "shop_level")
    private Integer shopLevel;
    @Column(name = "keepper_id")
    private Integer keepperId;

}
