package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 每日推荐
 */
@Getter
@Setter
public class Recommend {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 每日推荐列表
     */
    private ArrayList<RecommendList> recommendList = new ArrayList<>();

    /**
     * 每日推荐列表
     */
    @Getter
    @Setter
    public static class RecommendList {
        /**
         * 商品ID
         */
        private String id = "";
        /**
         * 淘宝商品ID
         */
        private String shopId = "";
        /**
         * 名字
         */
        private String name = "";
        /**
         * 头像
         */
        private String avatar = "";
        /**
         * 内容
         */
        private String content = "";
        /**
         * 内容   包含下单方法
         */
        private String contentall = "";
        /**
         * 建议发送时间
         */
        private String time = "";
        /**
         * 推荐理由
         */
        private String reason = "";
        /**
         * 类型
         */
        private String type = "";
        /**
         * 预估佣金
         */
        private String estimate = "";
        /**
         * 优惠券
         */
        private String discount = "";
        /**
         * 分享次数
         */
        private String position = "";
        /**
         * 主题ID
         */
        private String topicId = "";
        /**
         * 主题名字
         */
        private String topicName = "";

        /**
         * 商品名称
         */
        private String titles = "";
        /**
         * 商品图片
         */
        private String imgpic1 = "";
        /**
         * 优惠券id
         */
        private String couponid = "";

        /**
         * 商品名称
         */
        private String shopname = "";
        /**
         * 商品券后
         */
        private String postcouponprice = "";
        /**
         * 商品原价
         */
        private String shopprice = "";
        /**
         * 商品优惠券
         */
        private String  coupondenomination= "";
        /**
         * 商品佣金
         */
        private String precommission= "";
        private String commission= "";
        /**
         * 判断商品是否下架
         */
        private String shopinfoid= "";

        /**
         * 分享商品信息
         */
        private ShareInfo info = new ShareInfo();
        /**
         * 商品数据
         */
        private ArrayList<RecommendData> shopList = new ArrayList<>();

        /**
         * 多商品推荐
         */
        @Getter
        @Setter
        public static class RecommendData {
            /**
             * 商品ID
             */
            private String id = "";
            private String shopid="";
            /**
             * 价格
             */
            private String money = "";
            /**
             * 状态
             */
            private String status = "";
            /**
             * 图片地址
             */
            private String image = "";
        }
    }
}
