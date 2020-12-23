package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created By lhp
 * on 2020/6/4
 */
public class ZgCartInfo implements Serializable {

    /**
     * cartNum : 1
     * cartSumAmount : 3000
     * cartSumProfit : 75
     */

    private int cartNum;
    private BigDecimal cartSumAmount;
    private BigDecimal cartSumProfit;
    private int needNumToBuy;//差几件可以购买
    private int goodsCartNum;

    public int getGoodsCartNum() {
        return goodsCartNum;
    }

    public void setGoodsCartNum(int goodsCartNum) {
        this.goodsCartNum = goodsCartNum;
    }

    public int getNeedNumToBuy() {
        return needNumToBuy;
    }

    public void setNeedNumToBuy(int needNumToBuy) {
        this.needNumToBuy = needNumToBuy;
    }

    public int getCartNum() {
        return cartNum;
    }

    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }

    public BigDecimal getCartSumAmount() {
        return cartSumAmount;
    }

    public void setCartSumAmount(BigDecimal cartSumAmount) {
        this.cartSumAmount = cartSumAmount;
    }

    public BigDecimal getCartSumProfit() {
        return cartSumProfit;
    }

    public void setCartSumProfit(BigDecimal cartSumProfit) {
        this.cartSumProfit = cartSumProfit;
    }
}
