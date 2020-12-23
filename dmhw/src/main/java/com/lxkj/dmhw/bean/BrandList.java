package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取品牌列表
 */
@Getter
@Setter
public class BrandList {
    /**
     * 品牌id
     */
    private String id = "";
    /**
     * 中文名称
     */
    private String cn = "";
    /**
     * 英文名称
     */
    private String en = "";
    /**
     * 图片链接
     */
    private String url = "";
    /**
     * 是否选中，false为未选中
     */
    private boolean check = false;
}
