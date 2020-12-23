package com.lxkj.dmhw.bean;

import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 大家都在领
 */
@Getter
@Setter
public class Voucher {
    /**
     * 领取数量
     */
    private String vouchernum = "";
    /**
     * 商品列表
     */
    private ArrayList<VoucherData> shopdata = new ArrayList<>();

    /**
     * 商品列表
     */
    @Getter
    @Setter
    public static class VoucherData {
        /**
         * 商品ID
         */
        private String id = "";
        /**
         * 淘宝商品ID
         */
        private String shopid = "";
        /**
         * 商品图片
         */
        private String shopmainpic = "";
        /**
         * 原价
         */
        private String shopprice = "";
        /**
         * 商品名称
         */
        private String shopname = "";
        /**
         * 销量
         */
        private String shopmonthlysales = "";
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
         * @return 优惠券
         */
        public String getDiscount() {
            return (int) coupondenomination + "";
        }
    }
}
