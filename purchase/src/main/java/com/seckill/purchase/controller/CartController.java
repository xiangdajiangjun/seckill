/**
 * 购物车相关控制器
 */
package com.seckill.purchase.controller;

import com.seckill.purchase.service.CartService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
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
            cartService.addGood(userName,goodId);
            httpServletResponse.setStatus(200);
        }

    }
}
