package com.seckill.purchase.controller;

import com.seckill.purchase.service.OrderService;
import com.seckill.purchase.vo.OrderVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;


    @RequestMapping("/list")
    public String getAllOrder(Model model){
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        List<OrderVo> allOrder = orderService.getAllOrder(username);
        model.addAttribute("allorder",allOrder);
        return "order";
    }

    @RequestMapping("/status")
    public void receiveByBuyer(@RequestParam("orderId")Integer orderId, HttpServletResponse httpServletResponse){
        Boolean isSuccess = orderService.operateOderStatus(orderId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(204);

    }
    @RequestMapping("/return")
    public void returnGoods(@RequestParam("orderId")Integer orderId, HttpServletResponse httpServletResponse){
        Boolean isSuccess = orderService.returnGoods(orderId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(204);
    }
}
