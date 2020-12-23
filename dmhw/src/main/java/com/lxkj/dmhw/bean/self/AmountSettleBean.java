package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class AmountSettleBean implements Serializable {
    private Double activityReduce=0.0;//活动优惠
    private Double cashAmount=0.0;//当为玩主时候的可用虚拟余额
    private Double couponAmount=0.0;//优惠券优惠
    private Boolean ifVip;//是否是玩主
    private Boolean isCanUseCash=true;//是否可以使用余额 ture可以使用 false不可以使用
    private Double realPayAmount=0.0;//实付款
    private Double sumAmount=0.0;//商品金额
    private Double sumPostage=0.0;//邮费
    private Double sumProfile=0.0;//已省*元
    private Double sumTax=0.0;//税费
    private Double totalAmount=0.0;//小计
    private List<TaxDetailBean> taxDetails;//税费说明明显
    private String bottomPrompt;//底部提示信息
    private String balancePayMsg;//不能使用余额的原因
    private double score;//获得得积分
    private String sumTotalPostageAddress;//参与集货优惠金额文案
    private int userGrade;

    public int getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(int userGrade) {
        this.userGrade = userGrade;
    }

    public String getSumTotalPostageAddress() {
        return sumTotalPostageAddress;
    }

    public void setSumTotalPostageAddress(String sumTotalPostageAddress) {
        this.sumTotalPostageAddress = sumTotalPostageAddress;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<TaxDetailBean> getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(List<TaxDetailBean> taxDetails) {
        this.taxDetails = taxDetails;
    }

    public String getBottomPrompt() {
        return bottomPrompt;
    }

    public void setBottomPrompt(String bottomPrompt) {
        this.bottomPrompt = bottomPrompt;
    }

    public String getBalancePayMsg() {
        return balancePayMsg;
    }

    public void setBalancePayMsg(String balancePayMsg) {
        this.balancePayMsg = balancePayMsg;
    }

    public Boolean getCanUseCash() {
        return isCanUseCash;
    }

    public void setCanUseCash(Boolean canUseCash) {
        isCanUseCash = canUseCash;
    }

    public Double getActivityReduce() {
        return activityReduce;
    }

    public void setActivityReduce(Double activityReduce) {
        this.activityReduce = activityReduce;
    }

    public Double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Boolean getIfVip() {
        return ifVip;
    }

    public void setIfVip(Boolean ifVip) {
        this.ifVip = ifVip;
    }

    public Double getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(Double realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public Double getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Double sumAmount) {
        this.sumAmount = sumAmount;
    }

    public Double getSumPostage() {
        return sumPostage;
    }

    public void setSumPostage(Double sumPostage) {
        this.sumPostage = sumPostage;
    }

    public Double getSumProfile() {
        return sumProfile;
    }

    public void setSumProfile(Double sumProfile) {
        this.sumProfile = sumProfile;
    }

    public Double getSumTax() {
        return sumTax;
    }

    public void setSumTax(Double sumTax) {
        this.sumTax = sumTax;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
