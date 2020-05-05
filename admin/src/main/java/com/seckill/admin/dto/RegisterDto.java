package com.seckill.admin.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String email;
    private String shopName;
    private String type;

}
