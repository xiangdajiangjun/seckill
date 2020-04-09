package com.seckill.purchase.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartVo {
    private List<CartItem> cartListVoList;
    private double firstPrice;
    private double discount;
    private double priceTotal;

}
