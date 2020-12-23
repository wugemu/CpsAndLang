package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreateOrderBean implements Serializable {
    private double amount;
    private int ifCheckSms;//1-是 2-不需要短信校验，跳到支付页面进行微信支付宝支付 3-不需要，虚拟货币已覆盖商品金额，并且不需要进行短信校验（以后有可能会出现这种情况
    private String mobile;
    private long payCountDown;
    private String tradeNo;
    private boolean isShowTimer;//  true显示  false不显示
    private int waitSecond;//等待秒数
    private String notJoinGroupTitle;
    private int notJoinGroupType;

    public String getNotJoinGroupTitle() {
        return notJoinGroupTitle;
    }

    public void setNotJoinGroupTitle(String notJoinGroupTitle) {
        this.notJoinGroupTitle = notJoinGroupTitle;
    }

    public int getNotJoinGroupType() {
        return notJoinGroupType;
    }

    public void setNotJoinGroupType(int notJoinGroupType) {
        this.notJoinGroupType = notJoinGroupType;
    }

    public boolean isShowTimer() {
        return isShowTimer;
    }

    public void setShowTimer(boolean showTimer) {
        isShowTimer = showTimer;
    }

    public int getWaitSecond() {
        return waitSecond;
    }

    public void setWaitSecond(int waitSecond) {
        this.waitSecond = waitSecond;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getIfCheckSms() {
        return ifCheckSms;
    }

    public void setIfCheckSms(int ifCheckSms) {
        this.ifCheckSms = ifCheckSms;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getPayCountDown() {
        return payCountDown;
    }

    public void setPayCountDown(long payCountDown) {
        this.payCountDown = payCountDown;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
