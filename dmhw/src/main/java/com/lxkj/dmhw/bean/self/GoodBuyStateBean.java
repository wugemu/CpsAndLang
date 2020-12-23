package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class GoodBuyStateBean implements Serializable{
    private String goodsImgUrl;
    private String skuId;
    private boolean state;
    private int num=1;
    private long withinBuyId;

    public long getWithinBuyId() {
        return withinBuyId;
    }

    public void setWithinBuyId(long withinBuyId) {
        this.withinBuyId = withinBuyId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
