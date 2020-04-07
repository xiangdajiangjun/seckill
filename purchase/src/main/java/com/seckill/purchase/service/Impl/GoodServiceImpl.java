package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.service.GoodService;
import com.seckill.purchase.service.ImageService;
import com.seckill.purchase.utils.ConstantAll;
import com.seckill.purchase.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodServiceImpl implements GoodService {
    @Resource
    private ImageService imageService;
    @Resource
    private GoodDao goodDao;

    @Override
    public PageUtil getAllGoods(Integer currentPage) {
        //商品列表
        List<Goods> goodsList = goodDao.findAll().stream().filter(Goods::getIsSell).collect(Collectors.toList());
        //取得每个商品的图片base64码
        imageService.readImage(goodsList);
        goodsList.forEach(goods -> goods.setImage(ConstantAll.IMAGE_ENCODE+goods.getImage()));
        PageUtil<Goods> pageUtil =PageUtil.createPage(currentPage);
        try {
            pageUtil.doPage(goodsList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageUtil;
    }
    @Override
    public PageUtil getNewGoods(Integer currentPage) {
//        windowList = windowList.stream().filter(window -> window.getCreateDate().toLocalDateTime().toLocalDate().equals(LocalDate.now())).collect(Collectors.toList());
        //只返回当天上架的商品
        List<Goods> goodsList = goodDao.findAll().stream().filter(Goods::getIsSell).filter(goods -> goods.getCreateDate().toLocalDateTime().toLocalDate().equals(LocalDate.now())).collect(Collectors.toList());
        //取得每个商品的图片base64码
        imageService.readImage(goodsList);
        goodsList.forEach(goods -> goods.setImage(ConstantAll.IMAGE_ENCODE+goods.getImage()));
        PageUtil<Goods> pageUtil =PageUtil.createPage(currentPage);
        try {
            pageUtil.doPage(goodsList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageUtil;

    }
}
