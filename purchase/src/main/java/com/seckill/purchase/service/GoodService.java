package com.seckill.purchase.service;

import com.seckill.purchase.utils.PageUtil;

public interface GoodService {
    PageUtil getGoodsList(Integer currentPage,Integer type);
    PageUtil getNewGoods(Integer currentPage);
    PageUtil queryDoods(Integer currentPage,String queryStatement);
}
