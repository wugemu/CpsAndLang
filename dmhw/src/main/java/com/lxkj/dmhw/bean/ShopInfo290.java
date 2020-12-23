package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 店铺信息
 */
@Getter
@Setter
public class ShopInfo290 {
    /**
     * 店铺id
     */
    private String id = "";
    /**
     * 店铺名称
     */
    private String name = "";
    /**
     * 店铺链接
     */
    private String url = "";
    /**
     * 店铺图标
     */
    private String logo = "";

    /**
     * 店铺信息评分
     */
    private ArrayList<ShopInfoData> evaluates = new ArrayList<>();

    /**
     * 店铺信息评分
     */
    @Getter
    @Setter
    public static class ShopInfoData {
        /**
         * 项目
         */
        private String project = "";
        /**
         * 评分
         */
        private String score = "";
        /**
         * 等级
         */
        private String contrast = "";
        /**
         * 等级名称
         */
        private String contrastStr = "";


    }
}
