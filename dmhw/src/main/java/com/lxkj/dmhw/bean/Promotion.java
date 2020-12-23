package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 推广数据
 */
@Getter
@Setter
public class Promotion {
    /**
     * 功能号
     */
    private String funcid = "";
    /**
     * 功能名称
     */
    private String funcname = "";
    /**
     * 图标地址
     */
    private String funcico = "";
    /**
     * 链接地址
     */
    private String url = "";
}
