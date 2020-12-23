package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 注销账号初始化数据解析
 */
@Getter
@Setter
public class AccountDel {

    /**
     * 注销手机号
     */
    private String userphone = "";

    /**
     * 注销说明
     */
    private String aftermath = "";

    /**
     * 注销规则
     */
    private String ruleUrl = "";

    /**
     * 注销理由
     */
    private ArrayList<reasonData> ReasonList = new ArrayList<>();

    //数据
    @Getter
    @Setter
    public static class reasonData {

        /**
         * 名称
         */
        private String name = "";

        /**
         * 选中
         */
        private boolean  check= false;
    }
    /**
     * 数据列表
     */
    private ArrayList<Accontdata> LoseList = new ArrayList<>();


    //数据
    @Getter
    @Setter
    public static class Accontdata {

        /**
         * 名称
         */
        private String name = "";

        /**
         * 数据
         */
        private String value = "";
    }

}
