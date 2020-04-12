package com.seckill.purchase.service.Impl;

import com.google.gson.Gson;
import com.seckill.purchase.dao.GoodDao;
import com.seckill.purchase.entity.Goods;
import com.seckill.purchase.service.GoodService;
import com.seckill.purchase.service.ImageService;
import com.seckill.purchase.utils.ConstantAll;
import com.seckill.purchase.utils.PageUtil;
import com.seckill.purchase.vo.GoodListVo;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

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
//        windowList = windowList.stream().filter(window -> window.getCreateDate().toLocalDateTime().toLocalDate().equals(LocalDate.now())).collect(Collectors.toList());
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
//        solrQuery.setQuery("name:*人*");
        //solrQuery.set("fq","is_sell:true");
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
}


