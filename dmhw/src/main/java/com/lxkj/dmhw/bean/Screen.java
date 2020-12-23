package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 闪屏
 */
@Getter
@Setter
public class Screen {
    /**
     * 图片
     */
    private String advimg = "";
    /**
     * 跳转链接
     */
    private String jumpvaue = "";
    /**
     * 倒计时时间
     */
    private String countdown = "";
    /**
     * 跳转类型
     */
    private String jumptype = "";
    /**
     * 是否有广告
     */
    private String isexit = "";
    /**
     * 图片路径
     */
    private String path = "";
//    /**
//     * 活动id
//     */
//    private String advertisementid = "";
    /**
     * @return 是否有广告
     */
    public boolean isCheck() {
        return isexit.equals("1");
    }
}
