package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class TopNotifyBean implements Serializable {
    private int ifShow;//0不显示 1显示 是否显示
    private String picUrl;//顶部通知信息图片
    private String linkUrl;//顶部通知跳转地址

    public int getIfShow() {
        return ifShow;
    }

    public void setIfShow(int ifShow) {
        this.ifShow = ifShow;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
