package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaobaoBean {

    /**
     * 首页底部导航栏
     */
    /**
     *列表
     */
    private int couponNum;

    private ArrayList<TaobaoBean.BottomNavigation> NavList = new ArrayList<>();

    @Getter
    @Setter
    public static class BottomNavigation {
        /**
         * id
         */
        private String material = "";
        /**
         * 功能类型
         */
        private String type = "";
        /**
         * 名称
         */
        private String title = "";
        /**
         * 描述
         */
        private String desc = "";


    }
}
