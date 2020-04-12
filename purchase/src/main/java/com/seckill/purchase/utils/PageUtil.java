package com.seckill.purchase.utils;

import lombok.Data;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;
import sun.security.util.ArrayUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class PageUtil<T> {

    private Integer pageSize= ConstantAll.PAGE_SIZE;//一页显示条数 ;
    private boolean isFirstPage = false;//是否为第一页
    private boolean isLastPage = false;//是否为最后一页
    private boolean hasPreviousPage = true; //是否有前一页
    private boolean hasNextPage = true;//是否有下一页
    private Integer total;//条目总计
    private Integer pageTotal;//总页数
    private Integer currentPage;
    private List<T> list;


    private PageUtil(Integer currentPage){
        this.currentPage=currentPage;
    }
    public static PageUtil createPage(Integer currentPage){
        return new PageUtil(currentPage);
    }
    public static PageUtil createPage(Integer currentPage, Comparator comparator){
        return new PageUtil(currentPage);
    }
    public List<T> getList(){
        return this.list;
    }
    public void doPage(List<T> list) throws Exception {
        //计算分页各项信息
        total = list.size();
        pageTotal = total/pageSize;
        if((total%pageSize)!=0)
            pageTotal+=1;
        if(currentPage>pageTotal)
            throw new Exception("当前页超过总页数。");
        if (currentPage.equals(1))
        {
            isFirstPage=true;
            hasPreviousPage=false;
        }
        if(currentPage.equals(pageTotal)){
            isLastPage=true;
            hasNextPage=false;
        }

        //得到结果集合（总条目减去本页之前的条目再取pageSize条数据）
        long postPage=(currentPage-1)*pageSize;
        this.list = list.stream().skip(postPage).limit(pageSize).collect(Collectors.toList());
    }
    public void doPageForQuery(List<T> list,Integer numFound) throws Exception {
        total = numFound;
        pageTotal = total / pageSize;
        //若剩余不满一页的条目也算作一页
        if ((total % pageSize) != 0)
            pageTotal += 1;
        if (currentPage > pageTotal)
            throw new Exception("当前页超过总页数。");
        if (currentPage.equals(1)) {
            isFirstPage = true;
            hasPreviousPage = false;
        }
        if (currentPage.equals(pageTotal)) {
            isLastPage = true;
            hasNextPage = false;
        }
        //这里由于solr已经分页过了，所以根据solr的分页信息对应和计算自己的分页的信息即可，
        // 数据列表本身已经是分页后的结果，不需要根据这些分页来再截取，否则反而会返回不正确的内容。
        this.list = list;
    }
}
