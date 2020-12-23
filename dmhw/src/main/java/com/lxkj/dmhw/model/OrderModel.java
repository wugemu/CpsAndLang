package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.self.BasePage;
import com.lxkj.dmhw.bean.self.DeclarationDetailBean;
import com.lxkj.dmhw.bean.self.GoodBuyStateBean;
import com.lxkj.dmhw.bean.self.LogisticsBean;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.bean.self.PayResultReturnCash;
import com.lxkj.dmhw.bean.self.RefundLogistics;
import com.lxkj.dmhw.bean.self.RefundPost;
import com.lxkj.dmhw.bean.self.ShareBean;

import java.util.LinkedHashMap;
import java.util.List;

public class OrderModel extends BaseLangViewModel {
    private List<OrderBean> orderBeanList;
    private OrderBean orderBean;
    private List<BasePage<LogisticsBean>> logistMap;
    private double amount;//需要现金支付的金额
    private int ifCheckSms;//是否需要短信验证
    private String mobile;
    private String tradeNo;//订单号
    private long payCountDown;
    private List<GoodBuyStateBean> goodBuyStateBeanList;
    boolean isPayGift=false;//订单去支付 是否支付的礼包订单
    boolean isPayGroup=false;//订单去支付 是否支付的团购订单
    private ShareBean shareBean;
    private LinkedHashMap<String,List<RefundPost>> postMap;
    private List<RefundLogistics> refundLogistics;
    private DeclarationDetailBean declarationDetailBean;
    private String logisticsMobile;
    private PayResultReturnCash payResultReturnCash;

    public PayResultReturnCash getPayResultReturnCash() {
        return payResultReturnCash;
    }

    public void setPayResultReturnCash(PayResultReturnCash payResultReturnCash) {
        this.payResultReturnCash = payResultReturnCash;
    }

    public boolean isPayGroup() {
        return isPayGroup;
    }

    public void setPayGroup(boolean payGroup) {
        isPayGroup = payGroup;
    }

    public String getLogisticsMobile() {
        return logisticsMobile;
    }

    public void setLogisticsMobile(String logisticsMobile) {
        this.logisticsMobile = logisticsMobile;
    }

    public DeclarationDetailBean getDeclarationDetailBean() {
        return declarationDetailBean;
    }

    public void setDeclarationDetailBean(DeclarationDetailBean declarationDetailBean) {
        this.declarationDetailBean = declarationDetailBean;
    }

    public LinkedHashMap<String, List<RefundPost>> getPostMap() {
        return postMap;
    }

    public void setPostMap(LinkedHashMap<String, List<RefundPost>> postMap) {
        this.postMap = postMap;
    }

    public ShareBean getShareBean() {
        return shareBean;
    }

    public void setShareBean(ShareBean shareBean) {
        this.shareBean = shareBean;
    }

    public boolean isPayGift() {
        return isPayGift;
    }

    public void setPayGift(boolean payGift) {
        isPayGift = payGift;
    }

    public List<GoodBuyStateBean> getGoodBuyStateBeanList() {
        return goodBuyStateBeanList;
    }

    public void setGoodBuyStateBeanList(List<GoodBuyStateBean> goodBuyStateBeanList) {
        this.goodBuyStateBeanList = goodBuyStateBeanList;
    }

    public long getPayCountDown() {
        return payCountDown;
    }

    public void setPayCountDown(long payCountDown) {
        this.payCountDown = payCountDown;
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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public List<OrderBean> getOrderBeanList() {
        return orderBeanList;
    }

    public void setOrderBeanList(List<OrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public List<BasePage<LogisticsBean>> getLogistMap() {
        return logistMap;
    }

    public void setLogistMap(List<BasePage<LogisticsBean>> logistMap) {
        this.logistMap = logistMap;
    }

    public List<RefundLogistics> getRefundLogistics() {
        return refundLogistics;
    }

    public void setRefundLogistics(List<RefundLogistics> refundLogistics) {
        this.refundLogistics = refundLogistics;
    }

}
