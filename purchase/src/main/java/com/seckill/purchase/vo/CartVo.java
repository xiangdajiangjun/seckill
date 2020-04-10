package com.seckill.purchase.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartVo {
    private List<CartItem> cartListVoList;
    private double firstPrice;
    private double discount;
    private double priceTotal;

}
