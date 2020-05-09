package com.seckill.purchase.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
public class ActivitiesShop {
    private Integer id;
    private Integer shopId;
    private Integer activitiesId;
    private Integer goodsNum;
    private Timestamp createDate;
    private Timestamp updateDate;

}
