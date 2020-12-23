package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 收益具体内容
 */
@Getter
@Setter
public class Popularize {
    /**
     * 累计收益
     */
    private String accumulatedincome = "";
    /**
     * 已提现
     */
    private String withdrawalsmoney = "";
    /**
     * 未结算
     */
    private String waitsettleamount = "";
    /**
     * 推广数据
     */
    private ArrayList<PopularizeList> statisticsdata = new ArrayList<>();

    /**
     * 推广数据
     */
    @Getter
    @Setter
    public static class PopularizeList implements Serializable {
        /**
         * 标识
         */
        private String section = "";
        /**
         * 结算预估佣金
         */
        private String settlerincome = "";
        /**
         * 总成交收入
         */
        private String turnoverincome = "";
        /**
         * 总贡献量
         */
        private String turnovercount = "";
        /**
         * 我的成交收入
         */
        private String myturnoverincome = "";
        /**
         * 团队成交收入
         */
        private String teamturnoverincome = "";
        /**
         * 我的贡献量
         */
        private String myturnovercount = "";
        /**
         * 团队贡献量
         */
        private String teamturnovercount = "";

    }
}
