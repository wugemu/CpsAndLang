package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 我的服务
 */
@Getter
@Setter
public class MyService {
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
     * 是否登录
     */
    private String needlogin = "";
}
