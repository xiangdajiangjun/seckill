package com.seckill.purchase.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ActivitiesGoods implements Serializable {
    private Integer id;
    private String name;//商品名
    private Integer shopId;
    private Integer activitiesId;
    private Integer goodsId;
    private Double originalPrice;
    private Double presentPrice;//单价
    private Integer allLimit;
    private Integer singleLimit;
    private String buyerList;

    //附加用于前端显示的字段
    private String image;
    private String describe;//描述



    private Timestamp createDate;
    private Timestamp updateDate;
}
