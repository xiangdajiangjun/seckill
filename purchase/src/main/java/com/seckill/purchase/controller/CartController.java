/**
 * 购物车相关控制器
 */
package com.seckill.purchase.controller;

import com.seckill.purchase.service.CartService;
import com.seckill.purchase.vo.CartVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    @RequestMapping("/add")
    public void addGood(@RequestParam("goodid") Integer goodId, HttpServletResponse httpServletResponse){
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        if (userName!=null)
        {
            Boolean isSuccess = cartService.addGood(userName,goodId);
            if (isSuccess)
                httpServletResponse.setStatus(200);
            else
                httpServletResponse.setStatus(500);
        }

    }
    @RequestMapping("/list")
    public String seeList(Model model){
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        CartVo cartVo = cartService.seeCart(userName);
        if (cartVo==null)
            cartVo=new CartVo();
        model.addAttribute("cart",cartVo);
        return "cart";
    }
    @RequestMapping("/delete")
    public void deleteGoods(@RequestParam("goodid")Integer goodId,HttpServletResponse httpServletResponse){
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        Boolean isSuccess = cartService.deleteGoods(userName,goodId);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }

    @RequestMapping("/pay")
    public void payForCart(HttpServletResponse httpServletResponse){
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        Boolean isSuccess = cartService.payForCart(userName);
        if (isSuccess)
            httpServletResponse.setStatus(200);
        else
            httpServletResponse.setStatus(500);
    }
}
