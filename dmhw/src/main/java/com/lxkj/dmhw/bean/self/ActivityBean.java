package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class ActivityBean implements Serializable{

    private String bgColor;//背景色
    private String adrUrl;
    private String imgUrl;
    private String title;
    private long categoryId;//-1今日秒杀  -2每日上新  其它 以及一级分类
    private String name;
    private long id; //主题场id
    private String topImgUrl;//banner图片
    private String tagImgUrl;//标签图片
    private String countryNavIcon;
    private String countryId;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryNavIcon() {
        return countryNavIcon;
    }

    public void setCountryNavIcon(String countryNavIcon) {
        this.countryNavIcon = countryNavIcon;
    }

    public String getTagImgUrl() {
        return tagImgUrl;
    }

    public void setTagImgUrl(String tagImgUrl) {
        this.tagImgUrl = tagImgUrl;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getTopImgUrl() {
        return topImgUrl;
    }

    public void setTopImgUrl(String topImgUrl) {
        this.topImgUrl = topImgUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdrUrl() {
        return adrUrl;
    }

    public void setAdrUrl(String adrUrl) {
        this.adrUrl = adrUrl;
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
