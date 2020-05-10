package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.OrderDao;
import com.seckill.purchase.dao.UserDao;
import com.seckill.purchase.entity.ActivitiesGoods;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.entity.Order;
import com.seckill.purchase.service.ActivitiesRemote;
import com.seckill.purchase.service.GoodService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PublicService {
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
    
    public Boolean seckillOrder(Map activitiesGoodsId_buyerId) throws Exception {
        Integer activitiesGoodsId = (Integer) activitiesGoodsId_buyerId.get("activitiesGoodsId");
        Integer buyerId = (Integer) activitiesGoodsId_buyerId.get("buyerId");
        //提示结果语句
        List<String> seckillResult =seckillService.seeResult(buyerId);

        //检查秒杀库存
        ActivitiesGoods activitiesGoods = seckillService.getActivitiesGoods(activitiesGoodsId);
        if (activitiesGoods==null){
            String msg="活动"+activitiesGoods.getActivitiesId()+" : "+activitiesGoods.getId()+"号商品"+activitiesGoods.getName()+"抢单失败，商品不存在";
            seckillResult.add(msg);
            seckillService.saveResult(buyerId,seckillResult);
            return false;
        }
        if (activitiesGoods.getAllLimit()<=0){
            String msg="活动"+activitiesGoods.getActivitiesId()+" : "+activitiesGoods.getId()+"号商品"+activitiesGoods.getName()+"抢单失败，商品售罄";
            seckillResult.add(msg);
            seckillService.saveResult(buyerId,seckillResult);
            return false;
        }

        //得到购买者用户id列表
        List<Integer> buyerIdList = Arrays.stream(activitiesGoods.getBuyerList().split("\\s+")).filter(s -> !s.isEmpty())
                .map(Integer::valueOf).collect(Collectors.toList());
        Integer count = 0;
        for (Integer listId:buyerIdList){
            if (listId.equals(buyerId)){
                count+=1;
            }
        }
        if (activitiesGoods.getSingleLimit()<=count){
            String msg="活动"+activitiesGoods.getActivitiesId()+" : "+activitiesGoods.getId()+"号商品"+activitiesGoods.getName()+ "抢单失败，此商品限购"+activitiesGoods.getSingleLimit()+"件";
            seckillResult.add(msg);
            seckillService.saveResult(buyerId,seckillResult);
            return false;
        }

        //检查商品库存
        Goods goods = seckillService.findGoodsById(activitiesGoods.getGoodsId());
        if(goods==null){
            String msg="活动"+activitiesGoods.getActivitiesId()+" : "+activitiesGoods.getId()+"号商品"+activitiesGoods.getName()+ "抢单失败，商品不存在";
            seckillResult.add(msg);
            seckillService.saveResult(buyerId,seckillResult);
            return false;
        }
        else if (goods.getStock()<=0){
            String msg= "活动"+activitiesGoods.getActivitiesId()+" : "+activitiesGoods.getId()+"号商品"+activitiesGoods.getName()+"抢单失败，商品库存不足";
            seckillResult.add(msg);
            seckillService.saveResult(buyerId,seckillResult);
            return false;
        }

        //检查结束,保存临时结果
        String msg="活动"+activitiesGoods.getActivitiesId()+" : "+activitiesGoods.getId()+"号商品"+activitiesGoods.getName()+"抢单成功！";
        seckillResult.add(msg);
        seckillService.saveResult(buyerId,seckillResult);
        //秒杀商品库存变化
        activitiesGoods.setAllLimit(activitiesGoods.getAllLimit()-1);
        activitiesGoods.setBuyerList(activitiesGoods.getBuyerList()+" "+ buyerId);
        seckillService.saveActivitiesGoods(activitiesGoods);
        //创建订单保存redis,缓存过期未支付则消失
        Order order = Order.builder().orderTime(Timestamp.valueOf(LocalDateTime.now())).price(activitiesGoods.getPresentPrice()).buyerId(buyerId).goodsId(activitiesGoods.getGoodsId()).goodsName(activitiesGoods.getName()).goodsNum(1L).status(0).build();
        seckillService.saveOrder(order);
        return true;
    }
}
