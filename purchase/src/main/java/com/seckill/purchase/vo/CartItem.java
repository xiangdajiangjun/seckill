package com.seckill.purchase.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItem {
    private Integer goodId;
    private String goodName;
    private Long count;//数量
    private double price;//单价
    private double priceTotal;//总价
    private Boolean isSell;

}
