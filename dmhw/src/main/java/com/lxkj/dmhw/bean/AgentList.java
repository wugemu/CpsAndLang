package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 代理人数
 */
@Getter
@Setter
public class AgentList {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 代理数据
     */
    private ArrayList<AgentData> userdata = new ArrayList<>();

    /**
     * 代理数据
     */
    @Getter
    @Setter
    public static class AgentData {
        /**
         * 加入时间
         */
        private String jointime = "";
        /**
         * 头像
         */
        private String userpicurl = "";
        /**
         * 成员昵称
         */
        private String username = "";
        /**
         * 成员贡献
         */
        private String acccontributor = "";
        /**
         * 本月贡献金额
         */
        private String thismonthcontributor = "";
    }
}
