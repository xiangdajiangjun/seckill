package com.seckill.purchase.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodListVo {
    @Field("goods_id")
    private Integer goodId;
    private String name;//商品名
    private String code;//商品码
    private Long shopId;//店铺id
    @Field("price")
    private Double price;//单价
    private String stock;//库存
    private String describe;//描述
    private Integer salesVolume;//销量（总）
    @Field("image_url")
    private String img;//商品图片
}
