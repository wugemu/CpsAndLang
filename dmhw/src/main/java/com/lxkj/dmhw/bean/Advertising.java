package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 首页广告
 */
@Getter
@Setter
public class Advertising {
    /**
     * 广告是否存在
     */
    private String isexit = "";
    /**
     * 图片
     */
    private String advimg = "";
    /**
     * 活动id
     */
    private String advertisementlink = "";
    /**
     * 跳转链接
     */
    private String jumpvaue = "";
    /**
     * 跳转类型
     */
    private String jumptype = "";
    /**
     * 宽度
     */
    private Integer width = 600;
    /**
     * 高度
     */
    private Integer height = 600;


    /**
     * @return 是否有广告
     */
    public boolean isCheck() {
        return isexit.equals("1");
    }
}
