package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 首页活动热区
 */
@Getter
@Setter
public class MainHotTopic {

        /**
         * 样式类型
         */
        private String  layoutStyle= "";
        /**
         * 图片列表地址
         */
        private ArrayList<ImageUrl> imglist = new ArrayList<>();

        /**
         * 图片列表
         */
        @Getter
        @Setter
        public static class ImageUrl {
            /**
             * 图片地址
             */
            private String imgurl = "";
        }

}
