package com.seckill.purchase.api;

import com.seckill.purchase.dao.GoodTypeDao;
import com.seckill.purchase.entity.GoodType;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.service.AccountService;
import com.seckill.purchase.service.GoodService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/goods")
public class GoodsApi {
    @Resource
    private GoodService goodService;
    @Resource
    private AccountService accountService;
    @Resource
    private GoodTypeDao goodTypeDao;

    @RequestMapping(value = "/type/add")
    @ResponseBody
    Boolean addGoodsType(@RequestParam("tag") String tag){
        return goodService.addGoodsType(tag);
    }

    @RequestMapping(value = "/type")
    @ResponseBody
    public List<GoodType> getGoodsType(){
        return goodTypeDao.findAll();
    }

    @RequestMapping("/type/del")
    @ResponseBody
    public Boolean delType(@RequestParam("typeId") Integer typeId){
        Boolean aBoolean = goodService.delType(typeId);
        return aBoolean;
    }
    @RequestMapping("/type/changestatus")
    @ResponseBody
    public Boolean changeGoodsTypeStatus(@RequestParam("typeId") Integer typeId){
        return goodService.changeGoodsTypeStatus(typeId);
    }

    @RequestMapping("/all")
    @ResponseBody
    public List<Goods> getAllGoods(@RequestParam(value = "shopId") Integer shopId){
        List<Goods> goodsListForSeller = goodService.getGoodsListForSeller(shopId);
        return goodsListForSeller;
    }
    @RequestMapping("/del")
    @ResponseBody
    public Boolean delGoods(@RequestParam(value = "goodsId") Integer goodsId){
        Boolean isSuccess = goodService.delGoods(goodsId);
        return isSuccess;
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public Goods getGoods(@RequestParam(value = "goodsId") Integer goodsId){
        return goodService.getGoodsInfo(goodsId);
    }

    @RequestMapping(value = "/alter")
    @ResponseBody
    public Boolean alterGoodsInfo(@RequestBody Goods goodsDto){
        return goodService.alterGoods(goodsDto);
    }

    @RequestMapping(value = "/imguuid")
    @ResponseBody
    public String alterGoodsImgUuid(@RequestParam("goodsId") Integer goodsId){
        return goodService.getGoodsImgUUID(goodsId);
    }

    @RequestMapping(value = "/stock")
    @ResponseBody
    Boolean alterStock(@RequestBody Goods goodsDto){
        return goodService.alterStock(goodsDto);
    }

    @RequestMapping("/sellstatus")
    @ResponseBody
    Boolean changeSellStatus(@RequestParam("goodsId") Integer goodsId){
        return goodService.alterSell(goodsId);
    }
}
