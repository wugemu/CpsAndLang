package com.lxkj.dmhw.bean.self;

public class TradeSkuVO {
    private String cartId;
    private int num;
    private String skuId;
    private int refundNum;//退款数量
    private long withinBuyId;//内购场id
    private String videoId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public long getWithinBuyId() {
        return withinBuyId;
    }

    public void setWithinBuyId(long withinBuyId) {
        this.withinBuyId = withinBuyId;
    }

    public int getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(int refundNum) {
        this.refundNum = refundNum;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
