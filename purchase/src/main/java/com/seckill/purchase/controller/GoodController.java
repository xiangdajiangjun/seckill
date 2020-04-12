package com.seckill.purchase.controller;

import com.seckill.purchase.entity.GoodType;
import com.seckill.purchase.service.AccountService;
import com.seckill.purchase.service.GoodService;
import com.seckill.purchase.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/good")
public class GoodController {

    @Resource
    private GoodService goodService;
    @Resource
    private AccountService accountService;

    @RequestMapping("/query")
    public String query(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                        @RequestParam(value = "queryStatement")String queryStatement, Model model){
        //分类信息
        List<GoodType> goodTypeList = accountService.getGoodType();
        model.addAttribute("good_type",goodTypeList);
        //回传搜索语句
        model.addAttribute("queryStatement",queryStatement);
        //搜索结果
        PageUtil pageUtil = goodService.queryDoods(page, queryStatement);
        model.addAttribute("page",pageUtil);
        return "goodsquery";
    }

    @RequestMapping("/new")
    public String newGood(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page, Model model){
        //分类信息
        List<GoodType> goodTypeList = accountService.getGoodType();
        model.addAttribute("good_type",goodTypeList);
        //橱窗列表
        model.addAttribute("page",goodService.getNewGoods(page));
        return "newlist";
    }


    @RequestMapping("/list")
    public String goodList(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,@RequestParam(value = "typeId",required = false,defaultValue = "-1")Integer typeId, Model model){
        //分类信息
        List<GoodType> goodTypeList = accountService.getGoodType();
        model.addAttribute("good_type",goodTypeList);
        //橱窗列表
        model.addAttribute("page",goodService.getGoodsList(page,typeId));
        return "goodlist";
    }
}
