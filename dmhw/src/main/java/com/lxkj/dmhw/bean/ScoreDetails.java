package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 积分详情
 */
@Getter
@Setter
public class ScoreDetails {
    /**
     * 查询时间
     */
    private String searchtime = "";
    /**
     * 积分详情列表
     */
    private ArrayList<ScoreDetailsList> scoredetail = new ArrayList<>();

    /**
     * 积分详情列表
     */
    @Getter
    @Setter
    public static class ScoreDetailsList {
        /**
         * 积分
         */
        private String score = "";
        /**
         * 时间
         */
        private String lasttime = "";
    }
}
