package com.lxkj.dmhw.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 商学院首页数据
 */
@Getter
@Setter
public class ComCollegeData {
    /**
     * 轮播图列表
     */
    private ArrayList<Banner> BannerList = new ArrayList<>();

    /**
     * 轮播图广告实体
     */
    @Getter
    @Setter
    public static class Banner {
        /**
         * 广告ID
         */
        private String advertisemenid = "";
        /**
         * 图片地址
         */
        private String advertisementpic = "";
        /**
         * 跳转类型
         */
        private String jumptype = "";
        /**
         * 链接地址
         */
        private String advertisementlink = "";


        /**
         * 是否需要登录
         */
        private String needlogin = "";
        /**
         * 多商品ID
         */
        private String topicid = "";
    }

    /**
     * 轮播图以下的六宫格数据
     */
    private ArrayList<PartOne> partOneList = new ArrayList<>();

    @Getter
    @Setter
    public static class PartOne {
        /**
         * id
         */
        private String id = "";
        /**
         * 图片地址
         */
        private String imgurl = "";
        /**
         * 副标题
         */
        private String remark = "";
        /**
         * 分类id
         */
        private String sort = "";
        /**
         * 标题
         */
        private String title = "";
    }


    /**
     * 常见问题
     */
    private ArrayList<Question> QuestionList = new ArrayList<>();

    @Getter
    @Setter
    public static class Question {
        /**
         * id
         */
        private String id = "";
        /**
         * 图片地址
         */
        private String imgurl = "";
        /**
         * 副标题
         */
        private String remark = "";
        /**
         * 分类title
         */
        private String typetitle = "";
        /**
         * 标题
         */
        private String title = "";

        /**
         *点击数
         */
        private String clicknum = "";

    }
    /**
     * 新手视频
     */
    private ArrayList<NewHand> newHandList = new ArrayList<>();

    /**
     * 热门教程
     */
    private ArrayList<NewHand> hotList = new ArrayList<>();

    @Getter
    @Setter
    public static class NewHand {
        /**
         * id
         */
        private String id = "";
        /**
         * 时间
         */
        private String date = "";
        /**
         * 类别
         */
        private String type= "";
        /**
         * 图片地址
         */
        private String imgurl = "";
        /**
         * 副标题
         */
        private String remark = "";
        /**
         * 分类title
         */
        private String typetitle = "";
        /**
         * 标题
         */
        private String title = "";

        /**
         *点击数
         */
        private String clicknum = "";

        /**
         *分享数
         */
        private String sharenum = "";

        /**
         *视频URL
         */
        private String videourl = "";

    }
}
