package com.lxkj.dmhw.bean;

import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务列表
 */
@Getter
@Setter
public class MyTaskList {

    /**
     * 用户现金金额
     */
    private String scoremny="0";
    /**
     * 用户呆萌币数量
     */
    private String userscore="0";
    /**
     * 用户类型
     */
    private String usertype="";

    /**
     * 兑换现金比例
     */
    private String exchange="100";

    /**
     * 新人任务总数
     */
    private String freshtotalcnt="";
    /**
     * 日常任务总数
     */
    private String dailytotalcnt="";
    /**
     * 新人任务完成的个数
     */
    private String freshcnt="";
    /**
     * 日常任务完成的个数
     */
    private String dailycnt="";
    /**
     * 日常任务
     */
    public ArrayList<dailyItem> dailyList = new ArrayList<>();

    /**
     * 新手任务
     */
    public ArrayList<dailyItem> newHandList = new ArrayList<>();

    @Getter
    @Setter
    public static class dailyItem {
        /**
         * 类型
         */
        private String type="";
        /**
         * 任务标题
         */
        private String title="";
        /**
         * 副标题
         */
        private String remark="";
        /**
         * 奖励呆萌币的值
         */
        private String value="";
        /**
         * 完成次数
         */
        private String cnt="";
        /**
         * 奖励类型
         */
        private String model="";
        /**
         * 最多次数
         */
        private String max="";
        /**
         *获取金额
         */
        private String mny="";
        /**
         * 最多次数
         */
        private String ssid="";

        /**
         * 获取呆萌币
         */
        private String score="";

        /**
         * 用户类型
         */
        private String usertype="";

        /**
         * url
         */
        private String url="";

    }

}
