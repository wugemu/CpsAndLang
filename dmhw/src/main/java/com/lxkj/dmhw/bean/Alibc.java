package com.lxkj.dmhw.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 高佣常用数据
 */
@Getter
@Setter
public class Alibc {
    /**
     * 分享链接
     */
    private String shareshortlink = "";
    /**
     * 淘口令
     */
    private String couponlink = "";
    /**
     * 领券地址
     */
    private String pwd = "";
    /**
     * PID
     */
    private String pid = "";

    private Tips tips=new Tips();

    /**
     * 红包使用提示框实体
     */
    @Getter
    @Setter
    public static class Tips implements Serializable {
        /**
         * 显示内容
         */
        private String data ="";

        /**
         * 标题
         */
        private String title = "";

        /**
         * 节日KEY
         */
        private String key = "";
        /**
         * 比价
         */
        private String type = "";

        /**
         * 比价规则
         */
        private String ruleurl = "";

    }
}
