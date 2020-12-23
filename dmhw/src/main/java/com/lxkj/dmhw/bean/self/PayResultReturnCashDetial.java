package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created By lhp
 * on 2020/6/10
 */
public class PayResultReturnCashDetial implements Serializable {

    /**
     * createTime : 1591251149
     * endTime : 1591337549
     * id : 265208520000012
     * remainAmount : 10
     * returnableAmount : 10
     * splitTradeCnt : 1
     * state : 0
     * tradeNo : SD2006040057008901
     * updateTime : 1591251149
     * userId : 257519280000063
     */

    private long createTime;
    private long endTime;
    private String id;
    private BigDecimal remainAmount;
    private BigDecimal returnableAmount;
    private BigDecimal returnedAmount;
    private int splitTradeCnt;
    private int state;
    private String tradeNo;
    private long updateTime;
    private String userId;
    private int helpNum;
    private BigDecimal cashAmount;//余额

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getReturnedAmount() {
        return returnedAmount;
    }

    public void setReturnedAmount(BigDecimal returnedAmount) {
        this.returnedAmount = returnedAmount;
    }

    public int getHelpNum() {
        return helpNum;
    }

    public void setHelpNum(int helpNum) {
        this.helpNum = helpNum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getReturnableAmount() {
        return returnableAmount;
    }

    public void setReturnableAmount(BigDecimal returnableAmount) {
        this.returnableAmount = returnableAmount;
    }

    public int getSplitTradeCnt() {
        return splitTradeCnt;
    }

    public void setSplitTradeCnt(int splitTradeCnt) {
        this.splitTradeCnt = splitTradeCnt;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
