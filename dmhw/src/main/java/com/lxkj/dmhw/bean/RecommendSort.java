package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 营销素材分类
 */
@Getter
@Setter
public class RecommendSort {
    /**
     * 类型
     */
    private String itemtype = "";
    /**
     * 名称
     */
    private String itemname= "";

    /**
     * 是否选中
     */
    private int isSelect=0;

}
