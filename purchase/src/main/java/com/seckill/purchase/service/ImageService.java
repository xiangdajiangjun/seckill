package com.seckill.purchase.service;

import com.seckill.purchase.utils.ConstantAll;

public interface ImageService {
    String prefix = ConstantAll.GOOD_IMAGE_URL;
    String postFix = ConstantAll.IMAGE_TYPE_JPG;
    String writeImage(byte[] img);
    String readImage(Integer id);
}
