package com.seckill.purchase.service;

import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.entity.Window;
import com.seckill.purchase.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

public interface GoodService {
    PageUtil getAllWindow(Integer currentPage);
}
