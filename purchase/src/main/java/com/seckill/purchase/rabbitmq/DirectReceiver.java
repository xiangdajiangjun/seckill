package com.seckill.purchase.rabbitmq;

import com.seckill.purchase.service.Impl.PublicService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@RabbitListener(queues = "Seckill_activitiesGoodsId_buyerId")//监听的队列名称 Seckill_activitiesGoodsId_buyerId
public class DirectReceiver {
    @Resource
    private PublicService publicService;

    @RabbitHandler
    public void process(Map testMessage) throws Exception {
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
        publicService.seckillOrder(testMessage);
    }

}