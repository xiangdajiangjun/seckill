package com.seckill.purchase.service;

import com.seckill.purchase.utils.PageUtil;

public interface GoodService {
    PageUtil getAllGoods(Integer currentPage);
    PageUtil getNewGoods(Integer currentPage);
}
