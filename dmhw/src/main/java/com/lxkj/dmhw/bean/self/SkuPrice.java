package com.lxkj.dmhw.bean.self;

import java.math.BigDecimal;

/**
 * Created By lhp
 * on 2020/6/3
 */
public class SkuPrice {

    /**
     * batchBuyPrice : 900
     * createTime : 1584953682
     * goodsId : 256595020029523
     * id : 263151520018598
     * maxNum : 120
     * minNum : 10
     * price : 1000
     * profit : 25
     * skuId : 256615440008742
     * state : 1
     */

    private BigDecimal batchBuyPrice;
    private long createTime;
    private String goodsId;
    private String id;
    private int maxNum;
    private int minNum;
    private BigDecimal price;
    private BigDecimal profit;
    private String skuId;
    private int state;


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public BigDecimal getBatchBuyPrice() {
        return batchBuyPrice;
    }

    public void setBatchBuyPrice(BigDecimal batchBuyPrice) {
        this.batchBuyPrice = batchBuyPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
