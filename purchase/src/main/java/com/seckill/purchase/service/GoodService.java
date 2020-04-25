package com.seckill.purchase.service;

import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.utils.PageUtil;

import java.util.List;

public interface GoodService {
    PageUtil getGoodsList(Integer currentPage,Integer type);
    PageUtil getNewGoods(Integer currentPage);
    PageUtil queryDoods(Integer currentPage,String queryStatement);
    List getGoodsListForSeller(Integer shopId);
    Boolean delGoods(Integer goodsId);
    Goods getGoodsInfo(Integer goodsId);
    Boolean alterGoods(Goods goodsDto);
    String getGoodsImgUUID(Integer goodsId);
    Boolean alterStock(Goods goodsDto);
    Boolean alterSell(Integer goodsId);
}
