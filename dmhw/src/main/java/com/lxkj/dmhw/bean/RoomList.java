package com.lxkj.dmhw.bean;

import com.lxkj.dmhw.utils.Utils;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品列表
 */
@Getter
@Setter
public class RoomList {
        /**
         * 直播ID
         */
        private String id = "";
        /**
         * 淘宝商品ID
         */
        private String shopid = "";

        /**
         * 观看人数
         */
        private String num = "";

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
        private String shopprice = "0";
        /**
         * 推荐理由
         */
        private String recommended = "";
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
         * 券后价
         */
        private String postcouponprice ="0";


        /**
         * @return 券后价
         */
        public String getMoney() {
        if (shopprice!=null){
         return Utils.getFloat(Float.parseFloat(shopprice) - coupondenomination) + "";
         }
          return "0";
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
            return platformtype.equals("天猫");
        }
    }

