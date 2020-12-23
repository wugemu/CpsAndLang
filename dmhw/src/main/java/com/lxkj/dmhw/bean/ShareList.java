package com.lxkj.dmhw.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 分享数据
 */
@Getter
@Setter
public class ShareList {
    /**
     * ID
     */
    private String shopid = "";
    /**
     * 优惠券推广链接
     */
    private String couponlink = "";
    /**
     * 淘口令
     */
    private String tpwd = "";
    /**
     * 短链接
     */
    private String shareshortlink = "";

    private Tips tips=new Tips();

    /**
     * 红包使用提示框实体
     */
    @Getter
    @Setter
    public static class Tips extends Alibc.Tips implements Serializable {
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

    }
}
