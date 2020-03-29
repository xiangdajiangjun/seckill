package com.seckill.purchase.service;

public interface ImageService {
    String preflix = "D:\\IdeaProjects\\seckill\\image\\";
    String postFlix = ".jpg";
    String writeImage(byte[] img);
    byte[] readImage();
}
