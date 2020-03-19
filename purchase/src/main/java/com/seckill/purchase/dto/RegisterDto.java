package com.seckill.purchase.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String email;
}
