package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class ChangeTradeBean implements Serializable {
    private String tradeGroupCreateId;//团创建id
    private String tradeNo;//订单号

    public String getTradeGroupCreateId() {
        return tradeGroupCreateId;
    }

    public void setTradeGroupCreateId(String tradeGroupCreateId) {
        this.tradeGroupCreateId = tradeGroupCreateId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
