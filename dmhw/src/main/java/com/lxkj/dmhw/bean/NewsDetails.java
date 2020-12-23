package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息详情
 */
@Getter
@Setter
public class NewsDetails {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 消息详情列表
     */
    private ArrayList<NewsDetailsList> messagedata = new ArrayList<>();

    /**
     * 消息详情列表
     */
    @Getter
    @Setter
    public static class NewsDetailsList {
        /**
         * 消息编号
         */
        private String messageid = "";
        /**
         * 用户号
         */
        private String userid = "";
        /**
         * 消息标题
         */
        private String messagetitle = "";
        /**
         * 消息图片
         */
        private String messageimge = "";
        /**
         * 消息内容
         */
        private String messagecontent = "";
        /**
         * 消息状态
         */
        private String messagestatus = "";
        /**
         * 消息时间
         */
        private String createtime = "";
    }
}
