package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

/**
 * Created By lhp
 * on 2019/4/26
 */
public class MultipleSku implements Serializable {

    private String propertyName;
    private long specificationId;
    private String imgUrl;
    private int stockNum;
    private int stockGroupNum;


    public int getStockGroupNum() {
        return stockGroupNum;
    }

    public void setStockGroupNum(int stockGroupNum) {
        this.stockGroupNum = stockGroupNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public long getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(long specificationId) {
        this.specificationId = specificationId;
    }
}
