package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 粉丝成员
 */
@Getter
@Setter
public class FansInfo {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 粉丝成员数据
     */
    private ArrayList<FansList> userdata = new ArrayList<>();

    /**
     * 粉丝成员数据
     */
    @Getter
    @Setter
    public static class FansList {
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
    }
}
