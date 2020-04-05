package com.seckill.purchase.controllor;

import com.seckill.purchase.entity.GoodType;
import com.seckill.purchase.service.AccountService;
import com.seckill.purchase.service.GoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/good")
public class GoodControllor {

    @Resource
    private GoodService goodService;
    @Resource
    private AccountService accountService;
    @RequestMapping("/new")
    public String newGood(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page, Model model){
        //分类信息
        List<GoodType> goodTypeList = accountService.getGoodType();
        model.addAttribute("good_type",goodTypeList);
        //橱窗列表
        model.addAttribute("page",goodService.getNewWindow(page));
        return "newlist";
    }


    @RequestMapping("/list")
    public String goodList(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page, Model model){
        //分类信息
        List<GoodType> goodTypeList = accountService.getGoodType();
        model.addAttribute("good_type",goodTypeList);
        //橱窗列表
        model.addAttribute("page",goodService.getAllWindow(page));
        return "goodlist";
    }
}
