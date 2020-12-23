package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class PhoneLTBean implements Serializable {
    private String id;
    private String mobile;
    private String tradeNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
