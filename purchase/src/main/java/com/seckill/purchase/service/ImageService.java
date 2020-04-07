package com.seckill.purchase.service;

import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.utils.ConstantAll;

import java.util.List;

public interface ImageService {
    String prefix = ConstantAll.GOOD_IMAGE_URL;
    String postFix = ConstantAll.IMAGE_TYPE_JPG;
    String writeImage(byte[] img);
    String readImage(String imageId) ;
    String readImage(List<Goods> goodsList);
}
