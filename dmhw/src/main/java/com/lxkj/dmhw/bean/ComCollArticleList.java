package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 商学院文章详情数据
 title
 clicknum
 likenum
 sharenum
 id
 date
 */
@Getter
@Setter
public class ComCollArticleList {

        /**
         * id
         */
        private String id = "";
        /**
         * 时间
         */
        private String date = "";
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


}
