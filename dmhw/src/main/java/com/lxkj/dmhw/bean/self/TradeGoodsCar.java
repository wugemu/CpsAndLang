package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2018/9/29.
 */

public class TradeGoodsCar implements Serializable {


    /**
     * status : 1
     * appPrice : 790
     * cartId : 494150475390648300
     * deliveryType : 4
     * goodsId : 491667421149528060
     * goodsName : 兰测试商品7
     * imgUrl : http://image.mihui365.com/sd/middleImg/375189579268781.jpg
     * num : 5
     * profit : 0
     * stockCnt : 0
     * taxRate : 0
     */

    private boolean status;
    private double appPrice;
    private long cartId;
    private String deliveryType;
    private long goodsId;
    private String goodsName;
    private String imgUrl;
    private int num;
    private double profit;
    private int stockCnt;
    private double taxRate;
    private boolean ifPickOn;
    private String deliveryName;
    private String couponPolicyName;
    private int limitNum;
    private long skuId;
    private String skuName;
    private double returnAmount;
    private String returnDesc;
    private String returnTitle;
    private boolean isSellOut;
    private long withinBuyId;
    private long withinbuyEndTime;
    private boolean ifNewUserGoodsAdd;//是否新人专享商品
    private int ifLivePrice;//1直播中 2即将直播

    private String couponPolicyTag;//活动标签
    private int reachState;//活动状态 0_没有活动 1_未满足 2_以满足
    private String reachTitle;//活动提示文案
    private String reducePriceTitle;//比加入时降价文案
    private boolean ifShowLine;//是否显示分割线
    private long couponPolicyId;//活动id
    private String videoId;//短视频id

    private List<SkuPrice> skuPriceList;//专柜商品阶梯价格

    private int invalid;//失效商品=1 后面可能会替换

    public int getInvalid() {
        return invalid;
    }

    public void setInvalid(int invalid) {
        this.invalid = invalid;
    }

    public List<SkuPrice> getSkuPriceList() {
        return skuPriceList;
    }

    public void setSkuPriceList(List<SkuPrice> skuPriceList) {
        this.skuPriceList = skuPriceList;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public long getCouponPolicyId() {
        return couponPolicyId;
    }

    public void setCouponPolicyId(long couponPolicyId) {
        this.couponPolicyId = couponPolicyId;
    }

    public boolean isIfShowLine() {
        return ifShowLine;
    }

    public void setIfShowLine(boolean ifShowLine) {
        this.ifShowLine = ifShowLine;
    }

    public String getReducePriceTitle() {
        return reducePriceTitle;
    }

    public void setReducePriceTitle(String reducePriceTitle) {
        this.reducePriceTitle = reducePriceTitle;
    }

    public String getCouponPolicyTag() {
        return couponPolicyTag;
    }

    public void setCouponPolicyTag(String couponPolicyTag) {
        this.couponPolicyTag = couponPolicyTag;
    }

    public int getReachState() {
        return reachState;
    }

    public void setReachState(int reachState) {
        this.reachState = reachState;
    }

    public String getReachTitle() {
        return reachTitle;
    }

    public void setReachTitle(String reachTitle) {
        this.reachTitle = reachTitle;
    }

    public int getIfLivePrice() {
        return ifLivePrice;
    }

    public void setIfLivePrice(int ifLivePrice) {
        this.ifLivePrice = ifLivePrice;
    }

    public boolean isIfNewUserGoodsAdd() {
        return ifNewUserGoodsAdd;
    }

    public void setIfNewUserGoodsAdd(boolean ifNewUserGoodsAdd) {
        this.ifNewUserGoodsAdd = ifNewUserGoodsAdd;
    }

    public long getWithinbuyEndTime() {
        return withinbuyEndTime;
    }

    public void setWithinbuyEndTime(long withinbuyEndTime) {
        this.withinbuyEndTime = withinbuyEndTime;
    }

    public long getWithinBuyId() {
        return withinBuyId;
    }

    public void setWithinBuyId(long withinBuyId) {
        this.withinBuyId = withinBuyId;
    }

    public boolean isSellOut() {
        return isSellOut;
    }

    public void setSellOut(boolean sellOut) {
        isSellOut = sellOut;
    }

    public String getReturnTitle() {
        return returnTitle;
    }

    public void setReturnTitle(String returnTitle) {
        this.returnTitle = returnTitle;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getCouponPolicyName() {
        return couponPolicyName;
    }

    public void setCouponPolicyName(String couponPolicyName) {
        this.couponPolicyName = couponPolicyName;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public boolean isIfPickOn() {
        return ifPickOn;
    }

    public void setIfPickOn(boolean ifPickOn) {
        this.ifPickOn = ifPickOn;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }


    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



    public int getStockCnt() {
        return stockCnt;
    }

    public void setStockCnt(int stockCnt) {
        this.stockCnt = stockCnt;
    }

    public double getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(double appPrice) {
        this.appPrice = appPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
}
