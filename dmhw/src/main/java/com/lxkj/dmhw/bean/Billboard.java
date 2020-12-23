package com.lxkj.dmhw.bean;

import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 疯狂榜单
 */
@Getter
@Setter
public class Billboard {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 榜单数据
     */
    private ArrayList<BillboardData> shopdata = new ArrayList<>();

    /**
     * 榜单数据
     */
    @Getter
    @Setter
    public static class BillboardData {
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
         * 商品图片
         */
        private String shopmainpic = "";
        /**
         * 销量
         */
        private String shopmonthlysales = "";
        /**
         * 2个小时销量
         */
        private String twohournum = "";
        /**
         * 24小时销量
         */
        private String daynum = "";
        /**
         * 平台类型:天猫or淘宝
         */
        private String platformtype = "";
        /**
         * 原价
         */
        private String shopprice = "";
        /**
         * 预估佣金
         */
        private String precommission = "";
        /**
         * 优惠券
         */
        private float coupondenomination = 0;

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
         * @return 优惠券
         */
        public String getDiscount() {
            return (int) coupondenomination + "";
        }
    }
}
