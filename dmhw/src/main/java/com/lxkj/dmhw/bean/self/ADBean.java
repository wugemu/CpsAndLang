package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class ADBean implements Serializable{
    private List<CouponDialogBean>couponList;
    private String adaptImgUrl;
    private String imgUrl;
    private String linkUrl;
    private int flag;//1加载不显示 2显示

    public String getAdaptImgUrl() {
        return adaptImgUrl;
    }

    public void setAdaptImgUrl(String adaptImgUrl) {
        this.adaptImgUrl = adaptImgUrl;
    }

    public List<CouponDialogBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponDialogBean> couponList) {
        this.couponList = couponList;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
