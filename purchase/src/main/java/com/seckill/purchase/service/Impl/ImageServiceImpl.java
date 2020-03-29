package com.seckill.purchase.service.Impl;

import com.seckill.purchase.service.ImageService;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
    @Override
    public String writeImage(byte[] img) {
        String imageId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        System.out.println(img.length);
        try {
            OutputStream outputStream = new FileOutputStream(preflix+imageId+postFlix);
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
    public byte[] readImage() {
        return new byte[0];
    }
}
