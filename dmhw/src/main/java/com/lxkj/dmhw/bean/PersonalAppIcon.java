package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 我的服务
 */
@Getter
@Setter
public class PersonalAppIcon {
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
    /**
     * 图标类型（及位置 上到下）
     */
    private String type = "";

    /**
     * 是否需要登录
     */
    private String needlogin = "";

    /**
     * 描述
     */
    private String desc= "";
}
