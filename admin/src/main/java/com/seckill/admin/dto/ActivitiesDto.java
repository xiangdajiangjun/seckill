package com.seckill.admin.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ActivitiesDto {
    private String startDatetime;
    private String endDatetime;
    private Integer goodsLimit;
    private Integer shopLimit;
}
