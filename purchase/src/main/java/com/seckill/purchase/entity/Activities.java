package com.seckill.purchase.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class Activities {
    private Integer id;

    private Date startDatetime;
    private Date endDatetime;
    private String startDatetimeStr;
    private String endDatetimeStr;
    private Integer goodsLimit;
    private Integer shopLimit;
    private String creator;
    private Integer status;
    private Integer shopNum;
    private Timestamp createDate;
    private Timestamp updateDate;

}
