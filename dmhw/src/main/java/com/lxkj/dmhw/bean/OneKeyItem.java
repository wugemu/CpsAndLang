package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 一键转链
 */
@Getter
@Setter
public class OneKeyItem {
    /**
     * 转链内容
     */
    private String text = "";

    /**
     * 类目图片
     */
    private ArrayList<CommodityDetails290> goodsList;
}
