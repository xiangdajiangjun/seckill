package com.seckill.seller.service;

import com.seckill.seller.dto.GoodsDto;
import com.seckill.seller.entity.GoodType;
import com.seckill.seller.entity.Goods;
import com.seckill.seller.entity.Order;
import com.seckill.seller.utils.PageUtil;
import com.seckill.seller.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name= "purchase")
public interface PurchaseRemote {
    @RequestMapping(value = "/api/goods/all")
    List<Goods> getAllGoods(@RequestParam(value = "shopId") Integer shopId);
    @RequestMapping(value = "/api/goods/del")
    Boolean delGoods(@RequestParam(value = "goodsId") Integer goodsId);
    @RequestMapping(value = "/api/goods/get")
    Goods getGoods(@RequestParam(value = "goodsId") Integer goodsId);
    @RequestMapping(value = "/api/goods/type")
    List<GoodType> getGoodsType();
    @RequestMapping(value = "/api/goods/alter")
    Boolean alterGoodsInfo(@RequestBody Goods goodsDto);
    @RequestMapping(value = "/api/goods/imguuid")
    String alterGoodsImgUuid(@RequestParam("goodsId") Integer goodsId);
    @RequestMapping(value = "/api/goods/stock")
    Boolean alterStock(@RequestBody Goods goodsDto);
    @RequestMapping("/api/goods/sellstatus")
    Boolean changeSellStatus(@RequestParam("goodsId") Integer goodsId);

    @RequestMapping("/api/order/list")
    List<OrderVo> getAllOrderList(@RequestParam("shopId") Integer shopId);

    @RequestMapping("/api/order/status")
    Boolean changeOrderStatus(@RequestParam("shopId") Integer shopId);

    @RequestMapping("/api/order/agree")
    Boolean agreeApply(@RequestParam("orderId") Integer orderId);

    @RequestMapping("/api/order/refuse")
    Boolean refuseApply(@RequestParam("orderId") Integer orderId);
}
