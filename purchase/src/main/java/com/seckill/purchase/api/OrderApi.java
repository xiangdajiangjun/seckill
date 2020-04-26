package com.seckill.purchase.api;

import com.seckill.purchase.entity.Order;
import com.seckill.purchase.service.OrderService;
import com.seckill.purchase.vo.OrderVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api/order")
public class OrderApi {
    @Resource
    private OrderService orderService;

    @RequestMapping("/list")
    @ResponseBody
    public List<OrderVo> getAllOrderList(@RequestParam("shopId") Integer shopId){
        List<OrderVo> orderList = orderService.getOrderByShopId(shopId);
        return orderList;
    }

    @RequestMapping("status")
    @ResponseBody
    Boolean changeOrderStatus(@RequestParam("shopId") Integer shopId){
        return orderService.operateOderStatus(shopId);
    }
}
