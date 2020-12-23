package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderGoodBean implements Serializable {
    private String goodsImgUrl;
    private double goodsTax;
    private String refundState;//退款 0-正常,1-退款处理中,2-退款成功,3-退款驳回  4退款关闭)
    private String remark;
    private int sellCount;
    private double sellPrice;
    private double sellTotal;
    private String tradeGoodsNo;
    private String tradeId;
    private String tradeName;
    private String tradeSkuNo;
    private String unit;
    private String skuId;
    private int activityState;//-1商品已下架 0正常 无活动 1秒杀活动中
    private double goodsAmount;//省多少元

    private String goodsName;//售后订单列表使用
    private String refundId;
    private int refundNum;
    private String skuName;
    private double skuPrice;
    private String goodsId;
    private double returnAmount;
    private String returnDesc;
    private String returnTitle;
    private String delayedReminder;
    private long withinBuyId;

    public long getWithinBuyId() {
        return withinBuyId;
    }

    public void setWithinBuyId(long withinBuyId) {
        this.withinBuyId = withinBuyId;
    }

    public String getDelayedReminder() {
        return delayedReminder;
    }

    public void setDelayedReminder(String delayedReminder) {
        this.delayedReminder = delayedReminder;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public String getReturnTitle() {
        return returnTitle;
    }

    public void setReturnTitle(String returnTitle) {
        this.returnTitle = returnTitle;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public double getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(double skuPrice) {
        this.skuPrice = skuPrice;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public int getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(int refundNum) {
        this.refundNum = refundNum;
    }

    private boolean isChecked=true;//售后商品列表选择是否选中
    private int num;//售后商品列表选择数量

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getActivityState() {
        return activityState;
    }

    public void setActivityState(int activityState) {
        this.activityState = activityState;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public double getGoodsTax() {
        return goodsTax;
    }

    public void setGoodsTax(double goodsTax) {
        this.goodsTax = goodsTax;
    }

    public String getRefundState() {
        return refundState;
    }

    public void setRefundState(String refundState) {
        this.refundState = refundState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getSellTotal() {
        return sellTotal;
    }

    public void setSellTotal(double sellTotal) {
        this.sellTotal = sellTotal;
    }

    public String getTradeGoodsNo() {
        return tradeGoodsNo;
    }

    public void setTradeGoodsNo(String tradeGoodsNo) {
        this.tradeGoodsNo = tradeGoodsNo;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeSkuNo() {
        return tradeSkuNo;
    }

    public void setTradeSkuNo(String tradeSkuNo) {
        this.tradeSkuNo = tradeSkuNo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
