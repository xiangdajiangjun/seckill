package com.seckill.purchase.controller;

import com.seckill.purchase.dao.AccountDao;
import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.OrderDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.*;
import com.seckill.purchase.service.AccountService;
import com.seckill.purchase.service.ActivitiesRemote;
import com.seckill.purchase.service.GoodService;
import com.seckill.purchase.service.Impl.SeckillService;
import com.seckill.purchase.utils.MyDateUtils;
import com.seckill.purchase.utils.PageUtil;
import com.seckill.purchase.vo.CartItem;
import org.apache.shiro.SecurityUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RabbitTemplate rabbitTemplate;
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
    @Resource
    private SeckillService seckillService;

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
    public String seckAddGoods(@RequestParam("activitiesGoodsId") Integer activitiesGoodsId) throws Exception {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        Integer buyerId=userDao.findByUsername(userName).getId();
        //置入消息队列
        Map<String, Object> msg = new HashMap<>();
        msg.put("activitiesGoodsId",activitiesGoodsId);
        msg.put("buyerId",buyerId);
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", msg);
        return "操作成功,请关注抢单结果";
    }


    @RequestMapping("/result")
    public String seckResultPage(Model model){
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        Integer buyerId=userDao.findByUsername(userName).getId();
        List<String> resultMsg = seckillService.seeResult(buyerId);
        model.addAttribute("resultMsg",resultMsg);
        return "seckill_result_page";
    }

}
