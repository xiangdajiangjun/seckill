package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.WindowDao;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.entity.Window;
import com.seckill.purchase.service.GoodService;
import com.seckill.purchase.service.ImageService;
import com.seckill.purchase.utils.ConstantAll;
import com.seckill.purchase.utils.PageUtil;
import com.seckill.purchase.vo.GoodListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodServiceImpl implements GoodService {
    @Resource
    private ImageService imageService;
    @Resource
    private GoodDao goodDao;
    @Resource
    private WindowDao windowDao;

    @Override
    public PageUtil getAllWindow(Integer currentPage) {
        //橱窗列表
        List<Window> windowList = windowDao.findAll();
        //商品列表
        List<Integer> goodIdList = windowList.stream().map(Window::getGoodId).collect(Collectors.toList());
        //根据商品id映射的橱窗数据
        Map<Integer,Window> goodMapToWindow = windowList.stream()
                .collect(Collectors.toMap(Window::getGoodId,window->window,(k1,k2)->k1));
        List<Goods> goodsList = goodDao.findByIdIn(goodIdList);
        List<GoodListVo> goodListVoList = new ArrayList<>();
        GoodListVo goodListVo;

        for(Goods good:goodsList){
            Window window =goodMapToWindow.get(good.getId());
            //取得每个商品的图片base64码
            String img = ConstantAll.IMAGE_ENCODE+imageService.readImage(good.getId());
            goodListVo=GoodListVo.builder().code(good.getCode()).describe(window.getDescribe()).goodId(good.getId())
                    .name(good.getName()).price(window.getPrice()).salesVolume(window.getSalesVolume())
                    .shopId(good.getShopId()).stock(window.getStock()).windowId(window.getId()).img(img).build();
            goodListVoList.add(goodListVo);
        }


        PageUtil<GoodListVo> pageUtil =new PageUtil<GoodListVo>(currentPage);
        try {
            pageUtil.doPage(goodListVoList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageUtil;
    }
}
