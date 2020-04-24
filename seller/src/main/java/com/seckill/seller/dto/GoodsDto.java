package com.seckill.seller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class GoodsDto {
    private Integer id;
    private MultipartFile img;
}
