package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 首页模块
 */
@Getter
@Setter
public class MainModule {
    /**
     * 名称
     */
    private String name = "";
    /**
     * 图片地址
     */
    private String imageurl = "";
    /**
     * 类型
     */
    private String labeltype = "";
    /**
     * 高度
     */
    private String height = "";
    /**
     * 宽度
     */
    private String width = "";
    /**
     * 链接地址
     */
    private String url = "";
}
