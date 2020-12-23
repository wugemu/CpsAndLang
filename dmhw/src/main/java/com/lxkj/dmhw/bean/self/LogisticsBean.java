package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class LogisticsBean implements Serializable {
    private String context;
    private String ftimeString;
    private String logisticsName;
    private String message;
    private String postId;
    private String remark;
    private String statustxt;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFtimeString() {
        return ftimeString;
    }

    public void setFtimeString(String ftimeString) {
        this.ftimeString = ftimeString;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatustxt() {
        return statustxt;
    }

    public void setStatustxt(String statustxt) {
        this.statustxt = statustxt;
    }
}
