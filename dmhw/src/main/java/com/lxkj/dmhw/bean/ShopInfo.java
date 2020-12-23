package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 店铺信息
 */
@Getter
@Setter
public class ShopInfo {
    /**
     * 店铺名称
     */
    private String sellername = "";
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
    }
}
