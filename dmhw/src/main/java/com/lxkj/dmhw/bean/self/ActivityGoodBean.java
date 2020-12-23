package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class ActivityGoodBean implements Serializable {
    private String adrUrl;
    private String categoryId;
    private List<GoodBean> goodsList;
    private long id;//主题场id
    private String imgUrl;//大图
    private String title;
    private String withinBuyId;//内购场id

    public String getWithinBuyId() {
        return withinBuyId;
    }

    public void setWithinBuyId(String withinBuyId) {
        this.withinBuyId = withinBuyId;
    }

    public String getAdrUrl() {
        return adrUrl;
    }

    public void setAdrUrl(String adrUrl) {
        this.adrUrl = adrUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<GoodBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodBean> goodsList) {
        this.goodsList = goodsList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
