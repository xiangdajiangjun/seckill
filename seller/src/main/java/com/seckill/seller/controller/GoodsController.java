package com.seckill.seller.controller;

import com.seckill.seller.dao.ShopDao;
import com.seckill.seller.dto.GoodsDto;
import com.seckill.seller.entity.Goods;
import com.seckill.seller.entity.Shop;
import com.seckill.seller.service.AccountService;
import com.seckill.seller.service.GoodsRemote;
import com.seckill.seller.utils.ConstantAll;
import com.seckill.seller.utils.PageUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private GoodsRemote goodsRemote;
    @Resource
    private AccountService accountService;
    @Resource
    private ShopDao shopDao;

    @RequestMapping("/list")
    public String getGoodsList(Model model, @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage) throws Exception {
        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        int userId = accountService.getUserByUserName(keeperName).getId();
        Integer shopId = shopDao.findByKeepperId(userId).getId();
        List<Goods> allGoods = goodsRemote.getAllGoods(shopId);
        PageUtil<Goods> pageUtil = PageUtil.createPage(currentPage);
        pageUtil.doPage(allGoods);
        model.addAttribute("page",pageUtil);
        return "goodslist";
    }

    @RequestMapping("/del")
    public void delGoods(@RequestParam(value = "goodsid")Integer goodsId, HttpServletResponse httpServletResponse){
        Boolean isSuccess = goodsRemote.delGoods(goodsId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }

    @GetMapping("/info")
    public String goodsInfo(Model model,@RequestParam(value = "goodsid")Integer goodsId){
        model.addAttribute("goodstype",goodsRemote.getGoodsType());
        model.addAttribute("goods",goodsRemote.getGoods(goodsId));
        return "goodsinfo";
    }
    @RequestMapping("/alter")
    public void goodsAlter(Goods goodsDto,HttpServletResponse httpServletResponse){
        Boolean isSuccess = goodsRemote.alterGoodsInfo(goodsDto);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }
    @RequestMapping("/img")
    public void goodsImg(@RequestParam("id")Integer goodsId ,@RequestParam("img") MultipartFile img, HttpServletResponse httpServletResponse){
        if (img.isEmpty())
            return;
        String uuid = goodsRemote.alterGoodsImgUuid(goodsId);
        Boolean isSuccess=false;
        try {
            isSuccess=alertImage(uuid,img.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }
    public Boolean alertImage(String uuid,byte[] img) {
        System.out.println(img.length);
        try {
            OutputStream outputStream = new FileOutputStream(ConstantAll.GOOD_IMAGE_URL + uuid +ConstantAll.IMAGE_TYPE_JPG);
            outputStream.write(img);
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @GetMapping("/add")
    public String goodsAdd(@RequestParam(value = "goodsid")Integer goodsId){
        return null;
    }


}
