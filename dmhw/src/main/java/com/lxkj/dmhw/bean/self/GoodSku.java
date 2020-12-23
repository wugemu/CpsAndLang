package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lenovo on 2018/9/27.
 */

public class GoodSku implements Serializable {


    /**
     * barCode : 68569696
     * couponRate : 90
     * income : 8910
     * limitNum : 0
     * price : 99
     * salePrice : 0
     * skuId : 489843311046033400
     * skuImg : http://image.mihui365.com/sd/middleImg/1912718808433778.jpg
     * skuName : 一件套
     * skuNo : sgs7979
     * stockCnt : 99
     * unit : 90
     * unitCnt : 89
     * weight : 100
     */
    private long cartId;
    private int num;
    private double appPrice;
    private double taxRate;
    private String goodsName;
    private String barCode;
    private double couponRate;
    private double income;
    private int limitNum;
    private double price;
    private double salePrice;
    private long skuId;
    private String skuImg;
    private String imgUrl;
    private String skuName;
    private String skuNo;
    private int stockCnt;
    private String unit;
    private int unitCnt;
    private double weight;
    private String settlementTopPost;
    private String noDeliveryAranTips;//不发货或者路途遥远提示语
    private double profit;
    private double skuBackPrice;//sku 团长返额金额
    private String videoId;//短视频id

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public double getSkuBackPrice() {
        return skuBackPrice;
    }

    public void setSkuBackPrice(double skuBackPrice) {
        this.skuBackPrice = skuBackPrice;
    }

    double returnAmount;//反额 返券金额
    private String returnDesc;//反额 返券说明
    private String returnTitle;//反额 返券标题
    private List<Long> specificationList;

    private double groupPrice;
    private int groupStock;
    private long withinBuyId;

    public long getWithinBuyId() {
        return withinBuyId;
    }

    public void setWithinBuyId(long withinBuyId) {
        this.withinBuyId = withinBuyId;
    }

    public double getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(double groupPrice) {
        this.groupPrice = groupPrice;
    }

    public int getGroupStock() {
        return groupStock;
    }

    public void setGroupStock(int groupStock) {
        this.groupStock = groupStock;
    }

    public List<Long> getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(List<Long> specificationList) {
        this.specificationList = specificationList;
    }

    public String getNoDeliveryAranTips() {
        return noDeliveryAranTips;
    }

    public void setNoDeliveryAranTips(String noDeliveryAranTips) {
        this.noDeliveryAranTips = noDeliveryAranTips;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
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

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
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

    public double getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(double appPrice) {
        this.appPrice = appPrice;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSettlementTopPost() {
        return settlementTopPost;
    }

    public void setSettlementTopPost(String settlementTopPost) {
        this.settlementTopPost = settlementTopPost;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public double getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(double couponRate) {
        this.couponRate = couponRate;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public int getStockCnt() {
        return stockCnt;
    }

    public void setStockCnt(int stockCnt) {
        this.stockCnt = stockCnt;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getUnitCnt() {
        return unitCnt;
    }

    public void setUnitCnt(int unitCnt) {
        this.unitCnt = unitCnt;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
