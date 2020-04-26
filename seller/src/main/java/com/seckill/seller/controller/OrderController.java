package com.seckill.seller.controller;

import com.seckill.seller.entity.Order;
import com.seckill.seller.service.OrderService;
import com.seckill.seller.vo.OrderVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    private Map<String,Integer> orderStatus=new HashMap<>();
    {
        orderStatus.put("list",-1);
        orderStatus.put("wait",1);
        orderStatus.put("already",2);
        orderStatus.put("received",3);
        orderStatus.put("history",4);
    }

    @RequestMapping("/list")
    public String getAllOrderList(Model model,@RequestParam("type") String type){

        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        //得到本店主的所有订单
        List<OrderVo> orderList = orderService.getOrderListByKeeperName(keeperName);
        if (!type.equals("list")&& orderStatus.containsKey(type))
            orderList =orderList.stream().filter(orderVo -> orderVo.getStatus().equals(orderStatus.get(type))).collect(Collectors.toList());
        model.addAttribute("allorder",orderList);
        return "order";
    }

    @RequestMapping("/status")
    public void changeStatus(@RequestParam("orderId") Integer orderId, HttpServletResponse httpServletResponse){
        String keeperName = (String) SecurityUtils.getSubject().getPrincipal();
        Boolean isSuccess = orderService.changeStatus(keeperName,orderId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }


}
