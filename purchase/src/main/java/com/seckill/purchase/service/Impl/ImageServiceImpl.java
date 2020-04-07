package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.service.ImageService;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
    @Resource
    private GoodDao goodDao;

    @Override
    public String writeImage(byte[] img) {
        String imageId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        System.out.println(img.length);
        try {
            OutputStream outputStream = new FileOutputStream(prefix+imageId+postFix);
            outputStream.write(img);
            outputStream.close();
            return imageId;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String readImage(String imageId) {
        String imageBase64="";

        try {
            InputStream inputStream =new FileInputStream(prefix+imageId+postFix);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            Base64.Encoder encoder = Base64.getEncoder();
            imageBase64 = encoder.encodeToString(data);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBase64;
    }

    @Override
    public String readImage(List<Goods> goodsList) {
        goodsList.forEach(goods->goods.setImage(readImage(goods.getImage())));
        return null;
    }
}
