package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class GoodBannerImgBean implements Serializable {
    private String bannerImgUrl;
    private String bannerId;
    private String topImgUrl;//banner图片

    public String getTopImgUrl() {
        return topImgUrl;
    }

    public void setTopImgUrl(String topImgUrl) {
        this.topImgUrl = topImgUrl;
    }

    public String getBannerImgUrl() {
        return bannerImgUrl;
    }

    public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }
}
