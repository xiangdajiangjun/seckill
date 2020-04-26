package com.seckill.seller.controller;

import com.seckill.seller.dao.ShopDao;
import com.seckill.seller.entity.Goods;
import com.seckill.seller.service.AccountService;
import com.seckill.seller.service.PurchaseRemote;
import com.seckill.seller.utils.ConstantAll;
import com.seckill.seller.utils.PageUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private PurchaseRemote purchaseRemote;
    @Resource
    private AccountService accountService;
    @Resource
    private ShopDao shopDao;

    @RequestMapping("/list")
    public String getGoodsList(Model model, @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage) throws Exception {
        //根据本账号用户查出店铺id
        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        int userId = accountService.getUserByUserName(keeperName).getId();
        Integer shopId = shopDao.findByKeepperId(userId).getId();
        //取出店铺id所有商品然后分页包装
        List<Goods> allGoods = purchaseRemote.getAllGoods(shopId);
        PageUtil<Goods> pageUtil = PageUtil.createPage(currentPage);
        pageUtil.doPage(allGoods);
        model.addAttribute("page",pageUtil);
        return "goodslist";
    }

    @RequestMapping("/del")
    public void delGoods(@RequestParam(value = "goodsid")Integer goodsId, HttpServletResponse httpServletResponse){
        Boolean isSuccess = purchaseRemote.delGoods(goodsId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }

    @GetMapping("/info")
    public String goodsInfo(Model model,@RequestParam(value = "goodsid")Integer goodsId){
        model.addAttribute("goodstype", purchaseRemote.getGoodsType());
        model.addAttribute("goods", purchaseRemote.getGoods(goodsId));
        return "goodsinfo";
    }
    @RequestMapping("/alter")
    public void goodsAlter(Goods goodsDto,HttpServletResponse httpServletResponse){
        Boolean isSuccess = purchaseRemote.alterGoodsInfo(goodsDto);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }
    @RequestMapping("/img")
    public void goodsImg(@RequestParam("id")Integer goodsId ,@RequestParam("img") MultipartFile img, HttpServletResponse httpServletResponse){
        if (img.isEmpty())
            return;
        String uuid = purchaseRemote.alterGoodsImgUuid(goodsId);
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
    public String goodsAdd(Model model){
        model.addAttribute("goodstype", purchaseRemote.getGoodsType());
        return "goodsadd";
    }

    @PostMapping("/add")
    public void goodsAdd(Goods goodsDto,HttpServletResponse httpServletResponse){
        //根据本账号用户查出店铺id赋予新增商品
        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        int userId = accountService.getUserByUserName(keeperName).getId();
        Integer shopId = shopDao.findByKeepperId(userId).getId();
        goodsDto.setShopId(shopId);
        Boolean isSuccess = purchaseRemote.alterGoodsInfo(goodsDto);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }

    @GetMapping("/stock")
    public String getInfo(Model model, @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage) throws Exception {
        //根据本账号用户查出店铺id
        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        int userId = accountService.getUserByUserName(keeperName).getId();
        Integer shopId = shopDao.findByKeepperId(userId).getId();
        //取出店铺id所有商品然后分页包装
        List<Goods> allGoods = purchaseRemote.getAllGoods(shopId);
        PageUtil<Goods> pageUtil = PageUtil.createPage(currentPage);
        pageUtil.doPage(allGoods);
        model.addAttribute("page",pageUtil);
        return "goodsstock";
    }
    @PostMapping("/stock")
    public void alterStock(Goods goodsDto,HttpServletResponse httpServletResponse){
        Boolean isSuccess = purchaseRemote.alterStock(goodsDto);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }

    @RequestMapping("/sell")
    public String sellStatus(Model model,@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage, @RequestParam(value = "status") String status){
        //根据本账号用户查出店铺id
        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        int userId = accountService.getUserByUserName(keeperName).getId();
        Integer shopId = shopDao.findByKeepperId(userId).getId();
        //取出店铺id所有商品然后分页包装
        List<Goods> allGoods = purchaseRemote.getAllGoods(shopId);
        if (status.equals("is"))
            allGoods=allGoods.stream().filter(Goods::getIsSell).collect(Collectors.toList());
        else
            allGoods=allGoods.stream().filter(goods -> !(goods.getIsSell())).collect(Collectors.toList());
        PageUtil<Goods> pageUtil = PageUtil.createPage(currentPage);
        try {
            pageUtil.doPage(allGoods);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        model.addAttribute("page",pageUtil);
        return "goods_is_sell_list";
    }
    @RequestMapping("/sellstatus")
    public void changeSellStatus(@RequestParam(value = "goodsid") Integer goodsId,HttpServletResponse httpServletResponse){
        Boolean isSuccess = purchaseRemote.changeSellStatus(goodsId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }


}
