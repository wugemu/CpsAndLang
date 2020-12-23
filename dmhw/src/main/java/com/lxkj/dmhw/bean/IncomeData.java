package com.lxkj.dmhw.bean;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class IncomeData {
    /**
     * 1订单收益 2其他收益
     */
    private int preType;
    /**
     * 对应的金额
     */
    private BigDecimal realAmount;
    /**
     * 待到账金额
     */
    private BigDecimal estimatedAmount;
    /**
     * 订单号
     */
    private String tradeNo;
    /**
     * 状态 0待到账 2已到账 3取消收益
     */
    private int status;
    /**
     * 收益产生时间
     */
    private String date;

    public int getPreType() {
        return preType;
    }

    public void setPreType(int preType) {
        this.preType = preType;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public BigDecimal getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(BigDecimal estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
