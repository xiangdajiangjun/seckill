package com.seckill.purchase.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodListVo {
    private int goodId;
    private int windowId;
    private String name;//商品名
    private String code;//商品码
    private Long shopId;//店铺id
    private Double price;//单价
    private String stock;//库存
    private String describe;//描述
    private Integer salesVolume;//销量（总）
    private String img;//商品图片
}
