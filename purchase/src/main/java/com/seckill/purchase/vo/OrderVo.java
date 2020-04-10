package com.seckill.purchase.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderVo {
    private Integer orderId;
    private String goodName;
    private Long count;
    private double price;
    private double priceTotal;
    private Integer status;
}
