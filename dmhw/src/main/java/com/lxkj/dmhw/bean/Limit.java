package com.lxkj.dmhw.bean;

import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 抢购商品
 */
@Getter
@Setter
public class Limit {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 抢购商品列表
     */
    private ArrayList<LimitData> shopdata = new ArrayList<>();

    /**
     * 抢购商品列表
     */
    @Getter
    @Setter
    public static class LimitData {
        /**
         * 商品ID
         */
        private String id = "";
        /**
         * 淘宝商品ID
         */
        private String shopid = "";
        /**
         * 商品名称
         */
        private String shopname = "";
        /**
         * 平台类型:天猫or淘宝
         */
        private String platformtype = "";
        /**
         * 商品图片
         */
        private String shopmainpic = "";
        /**
         * 预估佣金
         */
        private String precommission = "";
        /**
         * 优惠券数量
         */
        private String couponcount = "";
        /**
         * 销量
         */
        private String shopmonthlysales = "";
        /**
         * 原价
         */
        private String shopprice = "";
        /**
         * 优惠券开始日期
         */
        private String couponstart = "";
        /**
         * 优惠券
         */
        private float coupondenomination = 0;
        /**
         * 优惠券剩余
         */
        private Integer couponresidue = 0;

        /**
         * 平台类型
         * @return 天猫(true)淘宝(false)
         */
        public boolean isCheck() {
            return platformtype.equals("天猫");
        }

        /**
         * @return 券后价
         */
        public String getMoney() {
            return Utils.getFloat(Float.parseFloat(shopprice) - coupondenomination) + "";
        }

        /**
         * @return 抢购进度
         */
        public String getProgress() {
            return Integer.parseInt(couponcount) - couponresidue + "";
        }

        /**
         * @return 优惠券
         */
        public String getDiscount() {
            return (int) coupondenomination + "";
        }
    }
}
