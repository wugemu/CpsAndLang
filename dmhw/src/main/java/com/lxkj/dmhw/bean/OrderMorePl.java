package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 推广订单
 */
@Getter
@Setter
public class OrderMorePl {
    /**
     * 查询时间
     */
    private String searchtime = "";
    private String tip = "";
    /**
     * 推广订单列表
     */
    private ArrayList<OrderList> data = new ArrayList<>();

    /**
     * 推广订单列表
     */
    @Getter
    @Setter
    public static class OrderList {
        /**
         * 订单编号
         */
        private String orderSn = "";
        /**
         * 图片地址
         */
        private String thumbnailUrl = "";
        /**
         * 商品名称
         */
        private String goodsName = "";
        /**
         * 商品数量
         */
        private String goodsQuantity = "";
        /**
         * 订单状态
         */
        private String orderStatus = "";
        /**
         * 订单状态
         */
        private String orderStatusName = "";
        /**
         * 付款金额/结算金额
         */
        private String orderAmount = "";
        /**
         * 收入来源
         */
        private String sourceName = "";

        /**
         * 创建时间
         */
        private String orderTime = "";
        /**
         * 结算时间
         */
        private String settleTime = "";
        /**
         * 收货时间
         */
        private String receiveTime = "";
        /**
         * 会员直推佣金
         */
        private String commission = "";
        /**
         * 比例
         */
        private String commissionRate = "";

        /**
         * 商品id
         */
        private String goodsId="";


        /**
         * 推广者佣金
         */
        private String agentCommission = "";


        /**
         * 平台
         */
        private Object cpsType=null;


        /**
         * 预计到账时间
         */
        private String accountTime="";


    }
}
