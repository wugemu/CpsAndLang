package com.lxkj.dmhw.bean;

import com.xiaomi.push.S;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 营销素材分类
 */
@Getter
@Setter
public class MarketSort {
    /**
     * 名称
     */
    private String title = "";
    /**
     * 标志
     */
    private String key= "";

    /**
     * 是否选中
     */
    private int isSelect=0;

}
