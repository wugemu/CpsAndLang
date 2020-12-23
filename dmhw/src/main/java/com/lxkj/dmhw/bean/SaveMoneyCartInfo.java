package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 省钱购物车实体类
 */
@Getter
@Setter
public class SaveMoneyCartInfo {

    /**
     * 优惠券数量
     */
    private String cnt= "";

    /**
     * 可为你省
     */
    private String save= "";
    /**
     * 可为你赚
     */
    private String comm= "";

    /**
     * 优惠券商品列表
     */
    private ArrayList<saveMoneyCartInfodata> SaveMoneyCartdata = new ArrayList<>();


    @Getter
    @Setter
    public static class saveMoneyCartInfodata {
        /**
         * 主图
         */
        private String shopmainpic = "";
        /**
         * 商品名称
         */
        private String shopname = "";
        /**
         * 商品id
         */
        private String shopid = "";
        /**
         * 商品价格
         */
        private String shopprice = "";
        /**
         * 预估佣金
         */
        private String precommission = "";
        /**
         * 优惠券价格
         */
        private String coupondenomination = "";
        /**
         * 优惠券链接
         */
        private String couponpromotionlink = "";
        /**
         * 获取pid
         */
        private String pid = "";
        /**
         * 商品销量
         */
        private String shopmonthlysales = "";
        /**
         * 券后价格
         */
        private String postcouponprice = "";
        /**
         * 平台类型
         */
        private String platformtype = "";
        /**
         * 店铺名称
         */
        private String sellername = "";
    }

}
