package com.lxkj.dmhw.bean;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 京东 拼多多专区
 */
@Getter
@Setter
public class CPSHomePage {
    /**
     * 超级分类列表
     */
    private ArrayList<JDSort> CategoryOneList = new ArrayList<>();


    /**
     * 超级分类列表
     */
    private ArrayList<CPSdata> BannerParentList = new ArrayList<>();


    //广告区域数据 轮播图 以及中间专区数据
    @Getter
    @Setter
    public static class CPSdata {

        /**
         * 类型
         */
        private String style = "00";
        /**
         * 轮播图列表
         */
        private ArrayList<JDBanner> BannerList = new ArrayList<>();


    }


}
