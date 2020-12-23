package com.lxkj.dmhw.bean;

import android.net.Network;

import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品列表
 */
@Getter
@Setter
public class CommodityList {
    /**
     * 查询时间
     */
    private String searchtime = "";

    /**
     * 商品名称
     */
    private String shopname = "";
    /**
     * 商品数据
     */
    private ArrayList<CommodityData> shopdata = new ArrayList<>();

    /**
     * 商品数据
     */
    @Getter
    @Setter
    public static class CommodityData {

        /**
         * 全网搜标记 0不是,1是
         */
        private int isNetwork=0;

        /**
         * 是否是第一项 0不是 ,1是
        */
        private int isFirstNetwork=0;


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
         * 商品短标题
         */
        private String shortname = "";
        /**
         * 商品主图
         */
        private String shopmainpic = "";
        /**
         * 商品价格
         */
        private String shopprice = "";
        /**
         * 预估佣金
         */
        private String precommission = "";
        /**
         * 销量
         */
        private String shopmonthlysales = "";
        /**
         * 平台类型:天猫(true)淘宝(false)
         */
        private String platformtype = "";
        /**
         * 商品视频地址
         */
        private String videolink = "";
        /**
         * 店铺名称
         */
        private String sellername = "";
        /**
         * 优惠券
         */
        private float coupondenomination = 0;

        /**
         * @return 券后价
         */
        public String getMoney() {
            return Utils.getFloat(Float.parseFloat(shopprice) - coupondenomination) + "";
        }

        /**
         * @return 商品名称
         */
        public String getTitle() {
            return shortname.equals("") ? shopname : shortname;
        }

        /**
         * @return 优惠券
         */
        public String getDiscount() {
            return (int) coupondenomination + "";
        }

        /**
         * 平台类型
         * @return 天猫(true)淘宝(false)
         */
        public boolean isCheck() {
            if (isNetwork==1){
             return platformtype.equals("1");
            }
            return platformtype.equals("天猫");
        }
    }
}
