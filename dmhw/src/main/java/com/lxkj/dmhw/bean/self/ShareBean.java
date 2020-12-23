package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class ShareBean implements Serializable {
    private String command;//口令
    private String des;
    private String imgUrl;//分享的小图（图文模式那种）
    private String linkUrl;
    private String sourceId;
    private String title;
    private String shareImgUrl;//分享的图片 大图
    private double profit;//至少赚
    private boolean state;//是否展示
    private int type;//1-app内跳转（包括跳到h5页面，地址为linkUrl）2-分享
    private String showImgUrl;//页面显示的图片地址
    private boolean isMin;//true小程序分享 false普通分享
    private String minPath;//小程序路由
    private String minAppId;//小程序appId
    private List<String> copywriting;


    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getCopywriting() {
        return copywriting;
    }

    public void setCopywriting(List<String> copywriting) {
        this.copywriting = copywriting;
    }

    public String getMinPath() {
        return minPath;
    }

    public void setMinPath(String minPath) {
        this.minPath = minPath;
    }

    public String getMinAppId() {
        return minAppId;
    }

    public void setMinAppId(String minAppId) {
        this.minAppId = minAppId;
    }

    public boolean isMin() {
        return isMin;
    }

    public void setMin(boolean min) {
        isMin = min;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getShowImgUrl() {
        return showImgUrl;
    }

    public void setShowImgUrl(String showImgUrl) {
        this.showImgUrl = showImgUrl;
    }

    public String getShareImgUrl() {
        return shareImgUrl;
    }

    public void setShareImgUrl(String shareImgUrl) {
        this.shareImgUrl = shareImgUrl;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
