package com.lxkj.dmhw.bean;

import com.lxkj.dmhw.view.inspectroom.GoodsInitUtile;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取品牌商品列表
 */
@Getter
@Setter
public class BigBrandList {
    /**
     * 品牌id
     */
    private String id = "";
    /**
     * 标志
     */
    private String logo = "";
    /**
     * 名称
     */
    private String brandName = "";

    /**
     * 品牌描述
     */
    private String desc = "";


    private ArrayList<CommodityDetails290> goodsList= new ArrayList<>();


}
