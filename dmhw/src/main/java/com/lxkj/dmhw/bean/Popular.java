package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 爆款推荐
 */
@Getter
@Setter
public class Popular {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 爆款推荐列表
     */
    private ArrayList<PopularData> burstingList = new ArrayList<>();

    /**
     * 爆款推荐列表
     */
    @Getter
    @Setter
    public static class PopularData {
        /**
         * 图片地址
         */
        private String shopmainpic = "";
        /**
         * 平台类型
         */
        private String platformtype = "";
        /**
         * 商品名称
         */
        private String shopname = "";
        /**
         * 商品ID
         */
        private String shopid = "";
        /**
         * 券后价
         */
        private String postcouponprice = "";
        /**
         * 预估佣金
         */
        private String precommission = "";
        /**
         * 跳转地址
         */
        private String supersearchlink = "";
        /**
         * 推送时间
         */
        private String senddate = "";
    }
}
