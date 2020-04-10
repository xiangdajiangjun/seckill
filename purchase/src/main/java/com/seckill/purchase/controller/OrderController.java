package com.seckill.purchase.controller;

import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.Order;
import com.seckill.purchase.entity.User;
import com.seckill.purchase.service.OrderService;
import com.seckill.purchase.utils.PageUtil;
import com.seckill.purchase.vo.OrderVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
}
