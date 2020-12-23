package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 推广订单
 */
@Getter
@Setter
public class Order {
    /**
     * 查询时间
     */
    private String searchtime = "";
    private String tip = "";
    /**
     * 是否设置比例
     */
    private String rate = "0";

    /**
     * 推广订单列表
     */
    private ArrayList<OrderList> orderdata = new ArrayList<>();

    /**
     * 推广订单列表
     */
    @Getter
    @Setter
    public static class OrderList {
        /**
         * 订单编号
         */
        private String orderid = "";
        /**
         * 图片地址
         */
        private String shopmainpic = "";
        /**
         * 商品名称
         */
        private String shopname = "";
        /**
         * 商品数量
         */
        private String shopnum = "";
        /**
         * 订单状态
         */
        private String orderstatus = "";
        /**
         * 付款金额
         */
        private String paymentamount = "";
        /**
         * 成交预估佣金
         */
        private String prediction = "";
        /**
         * 收入来源
         */
        private String advertisingname = "";
        /**
         * 结算金额
         */
        private String settlementamount = "";
        /**
         * 结算预估佣金
         */
        private String forecastincome = "";
        /**
         * 创建时间
         */
        private String createtime = "";
        /**
         * 收货时间
         */
        private String receiveTime = "";

        /**
         * 预计到账时间
         */
        private String accounttime = "";
        /**
         * 结算时间
         */
        private String settletime = "";
        /**
         * 会员直推佣金
         */
        private String commission = "";
        /**
         * 比例
         */
        private String ratio = "";

        //商品id
        private String shopid="";

        //是否有比价
        private String isbj="";


    }
}
