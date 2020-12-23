package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 推广订单
 */
@Getter
@Setter
public class ProfitMorePl {
    /**
     * 头部总览信息
     */
    private String availableAmt = "";
    private String extractedAmt = "";
    private String accumulatedAmt = "";
    private String unsettledAmt = "";

    /**
     * 类型信息
     */
    private ArrayList<profitList> cpsTypeList = new ArrayList<>();

    @Getter
    @Setter
    public static class profitList {
        /**
         * 平台类型
         */
        private String code = "";
        /**
         * 平台名称
         */
        private String name = "";
        /**
         * 平台logo
         */
        private String logo = "";

    }
}
