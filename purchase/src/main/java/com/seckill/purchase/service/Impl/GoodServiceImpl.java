package com.seckill.purchase.service.Impl;

import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.dao.GoodTypeDao;
import com.seckill.purchase.entity.GoodType;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.service.GoodService;
import com.seckill.purchase.service.ImageService;
import com.seckill.purchase.utils.ConstantAll;
import com.seckill.purchase.utils.PageUtil;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodServiceImpl implements GoodService {
    @Resource
    private SolrClient solrClient;
    @Resource
    private ImageService imageService;
    @Resource
    private GoodDao goodDao;
    @Resource
    private GoodTypeDao goodTypeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addGoodsType(String tag) {
        boolean isExist = goodTypeDao.findAll().stream().map(GoodType::getTag).collect(Collectors.toSet()).contains(tag);
        if (isExist)
            return false;
        else {
            GoodType goodType=new GoodType();
            goodType.setTag(tag);
            goodType.setIsAvailable(false);
            goodTypeDao.save(goodType);
            return true;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeGoodsTypeStatus(Integer typeId) {
        GoodType type = goodTypeDao.findById(typeId);
        type.setIsAvailable(!(type.getIsAvailable()));
        goodTypeDao.save(type);
        return true;
    }

    @Override
    @Transactional
    public Boolean delType(Integer typeId) {
        Set<Integer> type_isUsed = goodDao.findAll().stream().map(Goods::getTypeId).collect(Collectors.toSet());
        //类型标签已使用则删除失败，否则可删除
        if (type_isUsed.contains(typeId))
            return false;
        else
            goodTypeDao.deleteById(typeId);
        return true;
    }

    @Override
    public PageUtil getGoodsList(Integer currentPage,Integer type) {
        //商品列表
        List<Goods> goodsList = goodDao.findAll().stream().filter(Goods::getIsSell).collect(Collectors.toList());
        //类别码不为-1则返回相应分类
        if (!type.equals(-1))
            goodsList=goodsList.stream().filter(goods -> goods.getTypeId().equals(type)).collect(Collectors.toList());
        //取得每个商品的图片base64码
        return packgeImageAndPage(currentPage, goodsList);
    }
    @Override
    public PageUtil getNewGoods(Integer currentPage) {
        //只返回当天上架的商品
        List<Goods> goodsList = goodDao.findAll().stream().filter(Goods::getIsSell).filter(goods -> goods.getCreateDate().toLocalDateTime().toLocalDate().equals(LocalDate.now())).collect(Collectors.toList());
        //取得每个商品的图片base64码
        return packgeImageAndPage(currentPage, goodsList);

    }

    private PageUtil packgeImageAndPage(Integer currentPage, List<Goods> goodsList) {
        imageService.readImage(goodsList);
        goodsList.forEach(goods -> goods.setImage(ConstantAll.IMAGE_ENCODE+goods.getImage()));
        PageUtil<Goods> pageUtil =PageUtil.createPage(currentPage);
        try {
            pageUtil.doPage(goodsList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageUtil;
    }

    @Override
    public PageUtil queryDoods(Integer currentPage, String queryStatement) {
        PageUtil<Goods> page = PageUtil.createPage(currentPage);
        //拼接查询语句
        String nameLike = "name:*" + queryStatement +  "*";
        String desLike = " OR describe:*" + queryStatement+  "*";
        //设置查询条件
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q",nameLike+desLike);
        solrQuery.set("fq","is_sell:true");
        solrQuery.setStart(currentPage-1);
        solrQuery.setRows(ConstantAll.PAGE_SIZE);

        try {
            QueryResponse query = solrClient.query(solrQuery);
            SolrDocumentList results = query.getResults();
            long numFound = results.getNumFound();
            //得到结果集封装成实体集合

            List<Goods> goodsIdList = query.getBeans(Goods.class);
            if (goodsIdList==null||goodsIdList.isEmpty())
                return PageUtil.createPage(1);
            //图片处理成合适在前端展示
            goodsIdList.forEach(goods -> goods.setImage(ConstantAll.IMAGE_ENCODE+imageService.readImage(goods.getImage())));
            //封装分页（solr自身对分页有实现，所以这里是已经分页的结果了，但为了统一用自己的分页）
            page.doPageForQuery(goodsIdList,Integer.valueOf(Long.toString(numFound)));
            return page;

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }

    @Override
    public List<Goods> getGoodsListForSeller(Integer shopId) {
        //获取商品列表
        List<Goods> goodsList = goodDao.findAll().stream().filter(goods -> goods.getShopId().equals(shopId)).collect(Collectors.toList());

        return goodsList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean delGoods(Integer goodsId) {
        try{
            goodDao.deleteById(goodsId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Goods getGoodsInfo(Integer goodsId) {
        Goods goods = goodDao.findById(goodsId);
        String image = imageService.readImage(goods.getImage());
        image=ConstantAll.IMAGE_ENCODE+image;
        goods.setImage(image);
        return goods;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean alterGoods(Goods goodsDto) {
        if(goodsDto.getId()==0){
            goodsDto.setIsSell(false);
            goodsDto.setStock("0");
            goodsDto.setImage(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            goodDao.save(goodsDto);
        }else{
            Goods newGoods = goodDao.findById(goodsDto.getId());
            newGoods.setName(goodsDto.getName());
            newGoods.setCode(goodsDto.getCode());
            newGoods.setTypeId(goodsDto.getTypeId());
            newGoods.setDescribe(goodsDto.getDescribe());
            newGoods.setPrice(goodsDto.getPrice());
            goodDao.save(newGoods);
        }
        return true;
    }

    @Override
    public String getGoodsImgUUID(Integer goodsId) {
        return goodDao.findById(goodsId).getImage();
    }

    @Transactional
    @Override
    public Boolean alterStock(Goods goodsDto) {
        if (goodsDto.getId()==0||goodsDto.getStock()==null)
            return false;
        Goods newGoods = goodDao.findById(goodsDto.getId());
        newGoods.setStock(goodsDto.getStock());
        goodDao.save(newGoods);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean alterSell(Integer goodsId) {
        Goods goods = goodDao.findById(goodsId);
        goods.setIsSell(!goods.getIsSell());
        goodDao.save(goods);
        return true;
    }
}


