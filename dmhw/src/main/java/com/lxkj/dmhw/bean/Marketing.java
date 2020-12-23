package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 营销素材
 */
@Getter
@Setter
public class Marketing {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 营销素材列表
     */
    private ArrayList<MarketingList> marketingdata = new ArrayList<>();

    /**
     * 营销素材列表
     */
    @Getter
    @Setter
    public static class MarketingList {
        /**
         * ID
         */
        private String marketingid = "";
        /**
         * 名字
         */
        private String title = "";
        /**
         * 头像
         */
        private String imgpic = "";
        /**
         * 内容
         */
        private String content = "";
        /**
         * 视频地址
         */
        private String videourl = "";
        /**
         * 建议发送时间
         */
        private String sendtime = "";

        /**
         * 是否有视频 0-没有 1-有
         */
        private String existvideo = "";

        //评论
        private String comment = "";

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
}
