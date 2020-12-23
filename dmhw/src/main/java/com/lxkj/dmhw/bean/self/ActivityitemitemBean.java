package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class ActivityitemitemBean implements Serializable {
    private float height;
    private float width;
    private String imgUrl;
    private long themeId;
    private String adrUrl;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public String getAdrUrl() {
        return adrUrl;
    }

    public void setAdrUrl(String adrUrl) {
        this.adrUrl = adrUrl;
    }
}
