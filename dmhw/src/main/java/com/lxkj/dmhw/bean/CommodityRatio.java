package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品详情佣金比例
 */
@Getter
@Setter
public class CommodityRatio {
    /**
     * 佣金比例
     */
    private String ratio = "";
    /**
     * 店主佣金
     */
    private String superratio = "";
    /**
     * 分享页面显示佣金
     */
    private String agentratio = "";
}
