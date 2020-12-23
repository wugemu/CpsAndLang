package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class BreakUpBean implements Serializable {
    private boolean ifBreakUp;
    private String tradeId;

    public boolean isIfBreakUp() {
        return ifBreakUp;
    }

    public void setIfBreakUp(boolean ifBreakUp) {
        this.ifBreakUp = ifBreakUp;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }
}
