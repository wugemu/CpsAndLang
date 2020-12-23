package com.lxkj.dmhw.bean;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 积分总览
 */
@Getter
@Setter
public class ScoreOverview {
    /**
     * 可兑积分
     */
    private String convertibilitysocre = "";
    /**
     * 可兑金额
     */
    private String convertibilitycash = "";
    /**
     * 兑换比率
     */
    private String convertibilityratio = "";
    /**
     * 积分列表
     */
    private ArrayList<ScoreData> socredata = new ArrayList<>();

    /**
     * 积分列表
     */
    @Getter
    @Setter
    public static class ScoreData  {
        /**
         * 类型
         */
        private String stype = "";
        /**
         * 描述
         */
        private String desc = "";
        /**
         * 更新时间
         */
        private String lasttime = "";
    }
}
