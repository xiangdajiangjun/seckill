package com.seckill.purchase.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "window", schema = "db_seckill", catalog = "")
public class Window {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int GoodId;
    private Double price;
    private String stock;
    private String describe;
    private Integer salesVolume;
    private Timestamp createDate;
    private Timestamp updateDate;
}
