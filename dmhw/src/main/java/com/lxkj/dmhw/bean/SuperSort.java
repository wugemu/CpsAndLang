package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 超级分类
 */
@Getter
@Setter
public class SuperSort {

    /**
     * 代理数据
     */
    private ArrayList<dataTotal> subVoList = new ArrayList<>();

    /**
     * 二级数据
     */
    @Getter
    @Setter
    public static class dataTotal {
        /**
         * id
         */
        private String id = "";
        /**
         * logo
         */
        private String logo = "";
        /**
         * 名称
         */
        private String name = "";

        /**
         * 是否选择
         */
        private boolean check = false;

        //二级数据
        private ArrayList<Datatwo> twoList = new ArrayList<>();

        @Getter
        @Setter
        public static class Datatwo {
            /**
             * id
             */
            private String id = "";
            /**
             * logo
             */
            private String logo = "";
            /**
             * 名称
             */
            private String name = "";
        }
    }


}
