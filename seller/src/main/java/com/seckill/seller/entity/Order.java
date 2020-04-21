package com.seckill.seller.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "orders", schema = "db_seckill", catalog = "")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;//订单号
    @Basic
    private Integer buyerId;//下单用户id
    @Basic
    private Integer goodsId;//商品id
    private String goodsName;
    private Long goodsNum;//商品数量
    private double price;
    @Basic
    @Column(name = "status")
    private Integer status;//状态：0初始状态已下单；1已支付；2已发货；3已收货;4已关闭；
    private Timestamp orderTime;//下单时间
    private Timestamp payTime;//支付时间
    private Timestamp sendTime;//发货时间
    private Timestamp finishTime;//收货/完成时间

    @Column(name = "re_order_time")
    private Timestamp reOrderTime;//退单时间
    @Column(name = "re_send_time")
    private Timestamp reSendTime;//卖家收货
    @Column(name = "re_finish_time")
    private Timestamp reFinishTime;//退货/收货 完成时间
    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;

}
