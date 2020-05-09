package com.seckill.seller.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "activities", schema = "db_seckill", catalog = "")
public class Activities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date startDatetime;
    private Date endDatetime;
    private Integer goodsLimit;
    private Integer shopLimit;
    private String creator;
    private Integer status;
    private Integer shopNum;
    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;

}
