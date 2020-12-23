package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 分享海报
 */
@Getter
@Setter
public class Poster {
    /**
     * 分享地址
     */
    private String appsharelink = "";
    /**
     * 海报列表
     */
    private ArrayList<PosterList> posterdata = new ArrayList<>();

    /**
     * 海报列表
     */
    @Getter
    @Setter
    public static class PosterList {
        /**
         * 图片地址
         */
        private String posterurl = "";
        /**
         * 图片宽度
         */
        private Integer basewidth = 1080;
        /**
         * 图片高度
         */
        private Integer baselength = 1920;
        /**
         * 二维码X轴
         */
        private Integer xposition = 156;
        /**
         * 二维码Y轴
         */
        private Integer yposition = 1346;
        /**
         * 二维码宽度
         */
        private Integer width = 320;
        /**
         * 二维码高度
         */
        private Integer length = 320;
    }
}
