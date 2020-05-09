package com.seckill.purchase.controller;

import com.seckill.purchase.dao.AccountDao;
import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.OrderDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.*;
import com.seckill.purchase.service.AccountService;
import com.seckill.purchase.service.ActivitiesRemote;
import com.seckill.purchase.service.GoodService;
import com.seckill.purchase.utils.MyDateUtils;
import com.seckill.purchase.utils.PageUtil;
import com.seckill.purchase.vo.CartItem;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.util.ArrayUtil;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Resource
    private GoodService goodService;
    @Resource
    private ActivitiesRemote activitiesRemote;
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserDao userDao;
    @Resource
    private GoodDao goodDao;

    @RequestMapping("/list")
    public String seckList(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page, Model model) throws Exception {
        List<Activities> allActivities = activitiesRemote.getAllActivities().stream().filter(activities -> (activities.getStatus()==4)||(activities.getStatus()==5)).collect(Collectors.toList());
        allActivities.forEach(activities -> {
            activities.setStartDatetimeStr(MyDateUtils.dateToStr(activities.getStartDatetime(),null));
            activities.setEndDatetimeStr(MyDateUtils.dateToStr(activities.getEndDatetime(),null));
            //用户获取活动列表同时对4、5阶段进行时间判定
            Date now = new Date();
            if (activities.getStatus() == 4){
                if (now.compareTo(activities.getEndDatetime())>=0){
                    activities.setStatus(-1);
                    activitiesRemote.saveActivities(activities);
                }
                else if (now.compareTo(activities.getStartDatetime())>=0){
                    activities.setStatus(5);
                    activitiesRemote.saveActivities(activities);
                }
            }
            else if (activities.getStatus() == 5){
                if (now.compareTo(activities.getEndDatetime())>=0){
                    activities.setStatus(6);
                    activitiesRemote.saveActivities(activities);
                }
            }
        });
        PageUtil pageUtil = PageUtil.createPage(page);
        pageUtil.doPage(allActivities);
        model.addAttribute("page",pageUtil);
        return "activitieslist";
    }

    @RequestMapping("/goodslist")
    public String seckGoodsList(@RequestParam("activitiesId")Integer activitiesId,@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,Model model) throws Exception {
        //获取该次活动所有已上架商品
        List<ActivitiesGoods> activitiesGoodsList = activitiesRemote.getActivitiesGoodsList(activitiesId);
        List<Integer> goodsIdList = activitiesGoodsList.stream().map(ActivitiesGoods::getGoodsId).collect(Collectors.toList());
        //获取商品列表(已经过图片处理)
        List<Goods>goodsList = goodService.getGoodsListByIdList(goodsIdList);
        Map<Integer, String> goodsIdToImg = goodsList.stream().collect(Collectors.toMap(Goods::getId, Goods::getImage, (k1, k2) -> k1));
        Map<Integer, Double> goodsIdToPrice = goodsList.stream().collect(Collectors.toMap(Goods::getId, Goods::getPrice, (k1, k2) -> k1));
        activitiesGoodsList.forEach(activitiesGoods -> {
            activitiesGoods.setOriginalPrice(goodsIdToPrice.get(activitiesGoods.getGoodsId()));
            activitiesGoods.setImage(goodsIdToImg.get(activitiesGoods.getGoodsId()));
        });
        PageUtil pageUtil = PageUtil.createPage(page);
        pageUtil.doPage(activitiesGoodsList);
        model.addAttribute("page",pageUtil);
        return "seckillgoodslist";
    }

    @RequestMapping("/buygoods")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public String seckAddGoods(@RequestParam("activitiesGoodsId") Integer activitiesGoodsId){
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        Integer buyerId=userDao.findByUsername(userName).getId();
        ActivitiesGoods activitiesGoods = activitiesRemote.getActivitiesGoods(activitiesGoodsId);
        //检查1
        if (activitiesGoods==null)
            return "商品不存在";
        if (activitiesGoods.getAllLimit()<=0)
            return "商品售罄";
        //得到购买者用户id列表
        List<Integer> buyerIdList = Arrays.stream(activitiesGoods.getBuyerList().split("\\s+")).filter(s -> !s.isEmpty())
                .map(Integer::valueOf).collect(Collectors.toList());
        Integer count = 0;
        for (Integer listId:buyerIdList){
            if (listId.equals(buyerId)){
                count+=1;
            }
        }
        if (activitiesGoods.getSingleLimit()<=count)
            return "此商品限购"+activitiesGoods.getSingleLimit()+"件";
        //检查2
        Goods goods = goodDao.findById(activitiesGoods.getGoodsId());
        if(goods==null)
            return "商品不存在";
        else if (goods.getStock()<=0)
            return "商品库存不足";

        //成功,处理数据
        activitiesGoods.setAllLimit(activitiesGoods.getAllLimit()-1);
        activitiesGoods.setBuyerList(activitiesGoods.getBuyerList()+" "+ buyerId);
        goods.setStock(goods.getStock()-1);
        //创建订单
        Order order = Order.builder().orderTime(Timestamp.valueOf(LocalDateTime.now())).price(activitiesGoods.getPresentPrice()).buyerId(buyerId).goodsId(activitiesGoods.getGoodsId()).goodsName(activitiesGoods.getName()).goodsNum(1L).status(1).build();
        activitiesRemote.saveActivitiesGoods(activitiesGoods);
        goodDao.save(goods);
        orderDao.save(order);
        return "购买成功";
    }
}
