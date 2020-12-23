package com.lxkj.dmhw.bean;

import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 京东商品列表
 */
@Getter
@Setter
public class JDGoodsBean {

        /**
         * 商品分类ID
         */
        private String categoryId = "";
        /**
         * 商品ID
         */
        private String id = "";

        /**
         * 商品名称
         */
        private String name = "";

        /**
         * 商品主图
         */
        private String imageUrl = "";
        /**
         * 商品原价
         */
        private String cost = "";
        /**
         * 佣金
         */
        private String commission = "";
        /**
         * 不同身份对应佣金
         */
        private String normalCommission = "";
        /**
         * 佣金比例
         */
        private String commissionRate = "";
        /**
         * 折扣
         */
        private String discount = "";

        /**
         * 券后价
         */
        private String price= "";
        /**
         * 销量
         */
        private String sales = "";


        //优惠券信息
        private Object couponInfo;

        //店铺信息
        private Object shopInfo;

        private Object cpsType;

        //券额
        private String save="";

        /**
         * @return 优惠券
         */
//        public String getCoupon() {
//                return Utils.getFloat(Float.parseFloat(cost) - Float.parseFloat(price)) + "";
//        }

}
