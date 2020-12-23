package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 商学院文章详情数据
 * title
 * clicknum
 * likenum
 * sharenum
 * islike
 * type
 * imgurl
 * type
 * content
 * videourl
 * id
 * date
 */
@Getter
@Setter
public class ComCollArticle {

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
     * 类别
     */
    private String label= "";
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
       *赞数
       */
       private String likenum = "";
      /**
       *是否点赞
       */
       private String islike = "";

    /**
     *文章内容
     */
    private String htmlurl = "";

    /**
     *视频
     */
    private String videourl = "";



}
