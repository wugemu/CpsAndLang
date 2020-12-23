package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class TradeCountBean implements Serializable {
    private int finishCount;//已完成订单数量
    private int notPayCount;//未支付订单数量
    private int notSendCount;//未发货订单数量
    private int rerurnCount;//退款订单数量
    private int takeCount;//发货订单数量

    public int getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(int finishCount) {
        this.finishCount = finishCount;
    }

    public int getNotPayCount() {
        return notPayCount;
    }

    public void setNotPayCount(int notPayCount) {
        this.notPayCount = notPayCount;
    }

    public int getNotSendCount() {
        return notSendCount;
    }

    public void setNotSendCount(int notSendCount) {
        this.notSendCount = notSendCount;
    }

    public int getRerurnCount() {
        return rerurnCount;
    }

    public void setRerurnCount(int rerurnCount) {
        this.rerurnCount = rerurnCount;
    }

    public int getTakeCount() {
        return takeCount;
    }

    public void setTakeCount(int takeCount) {
        this.takeCount = takeCount;
    }
}
