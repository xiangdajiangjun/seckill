package com.seckill.admin.service.Impl;


import com.seckill.admin.entity.GoodType;
import com.seckill.admin.service.PurchaseRemote;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("typeService")

public class TypeService {
    @Resource
    private PurchaseRemote purchaseRemote;

    public Boolean addType(String tag){
        return purchaseRemote.addGoodsType(tag);
    }

    public Boolean changeStatus(Integer typeId){
        return purchaseRemote.changeGoodsTypeStatus(typeId);
    }

    public List<GoodType> getTypeList(){
        List<GoodType> goodTypeList = purchaseRemote.getGoodsType();
        return goodTypeList;
    }

    public  Boolean delType(Integer typeId){
        return purchaseRemote.delType(typeId);
    }
}
