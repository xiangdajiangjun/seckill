package com.seckill.purchase.service;

import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.utils.PageUtil;

import java.util.List;

public interface GoodService {
    Boolean delType(Integer typeId);
    PageUtil getGoodsList(Integer currentPage,Integer type);
    PageUtil getNewGoods(Integer currentPage);
    PageUtil queryDoods(Integer currentPage,String queryStatement);
    List<Goods> getGoodsListForSeller(Integer shopId);
    Boolean delGoods(Integer goodsId);
    Goods getGoodsInfo(Integer goodsId);
    Boolean alterGoods(Goods goodsDto);
    String getGoodsImgUUID(Integer goodsId);
    Boolean alterStock(Goods goodsDto);
    Boolean alterSell(Integer goodsId);

    Boolean changeGoodsTypeStatus(Integer typeId);

    Boolean addGoodsType(String tag);
}
