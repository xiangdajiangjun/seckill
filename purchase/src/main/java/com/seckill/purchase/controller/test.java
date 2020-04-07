package com.seckill.purchase.controller;

import com.seckill.purchase.dao.AccountDao;
import com.seckill.purchase.dao.RoleDao;
import com.seckill.purchase.entity.Account;
import com.seckill.purchase.entity.Role;
import com.seckill.purchase.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("/test")
public class test {
    @Resource
    private ImageService imageService;
    @Resource
    private AccountDao accountDao;
    @Resource
    private RoleDao roleDao;
    @GetMapping("/waluduo")
    public String test(){
        Account account = accountDao.findAccountByUsername("xiang");
        Role relo = roleDao.findById(1);
        account.getRoleList().add(relo);
        //accountDao.save(account);
        return account.getRoleList().toString();
    }

    @GetMapping("/img")
    public String img(Model model){
        String img = imageService.readImage("");
        model.addAttribute("img1","data:image/jpg;base64,"+img);
        return "test";
    }

    @ResponseBody
    @PostMapping("/img")
    public String imgW(@RequestParam("img") MultipartFile img) throws IOException {


        return imageService.writeImage(img.getBytes());
    }

    @ResponseBody
    @GetMapping("/imgR")
    public String imgR(Model model) throws IOException {
        String img = imageService.readImage("");
        model.addAttribute("img1",img);

        return img;
    }
}
