package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 导航栏
 */
@Getter
@Setter
public class Menu {
    /**
     * 菜单功能号
     */
    private String menuid = "";
    /**
     * 未选中图标
     */
    private String menudefaultico = "";
    /**
     * 菜单名称
     */
    private String menuname = "";
    /**
     * 选中图标
     */
    private String menuclickico = "";
}
