package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取用户收藏
 */
@Getter
@Setter
public class Collection {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 收藏商品列表
     */
    private ArrayList<CollectionList> shopdata = new ArrayList<>();

    /**
     * 收藏商品列表
     */
    @Getter
    @Setter
    public static class CollectionList {
        /**
         * 商品ID
         */
        private String id = "";
        /**
         * 淘宝商品ID
         */
        private String shopId = "";
        /**
         * 收藏ID
         */
        private String collectionId = "";
        /**
         * 商品名称
         */
        private String title = "";
        /**
         * 商品图片
         */
        private String image = "";
        /**
         * 优惠券
         */
        private String discount = "";

        /**
         * 原价
         */
        private String shopprice = "";

        /**
         * 销量
         */
        private String sales = "";
        /**
         * 价格
         */
        private String money = "";
        /**
         * 预估佣金
         */
        private String estimate = "";
        /**
         * 短链接
         */
        private String shortLink = "";
        /**
         * 推荐文案
         */
        private String recommend = "";
        /**
         * 店铺名称
         */
        private String shopName = "";
        /**
         * 平台类型:天猫(true)淘宝(false)
         */
        private boolean check = false;
        /**
         * 是否选中:选中(true)未选中(false)
         */
        private boolean play = false;
        /**
         * 是否失效:未失效(true)失效(false)
         */
        private boolean effect = false;
    }
}
