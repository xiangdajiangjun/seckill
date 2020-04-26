package com.seckill.seller.service.Impl;

import com.seckill.seller.dao.ShopDao;
import com.seckill.seller.entity.Order;
import com.seckill.seller.service.AccountService;
import com.seckill.seller.service.OrderService;
import com.seckill.seller.service.PurchaseRemote;
import com.seckill.seller.vo.OrderVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private AccountService accountService;
    @Resource
    private ShopDao shopDao;
    @Resource
    private PurchaseRemote purchaseRemote;

    @Override
    public List<OrderVo> getOrderListByKeeperName(String keeperName) {
        int userId = accountService.getUserByUserName(keeperName).getId();
        Integer shopId = shopDao.findByKeepperId(userId).getId();
        List<OrderVo> allOrderList = purchaseRemote.getAllOrderList(shopId);
        return allOrderList;
    }

    @Override
    public Boolean changeStatus(String keeperName,Integer orderId) {
        List<OrderVo> allOrderList = getOrderListByKeeperName(keeperName);
        for (OrderVo orderVo:allOrderList){
            if (orderVo.getOrderId().equals(orderId)){
                return purchaseRemote.changeOrderStatus(orderId);
            }
        }
        return false;
    }
}
