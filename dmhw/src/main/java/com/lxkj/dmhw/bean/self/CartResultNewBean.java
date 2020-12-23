package com.lxkj.dmhw.bean.self;

import java.math.BigDecimal;
import java.util.List;

public class CartResultNewBean {
    private BigDecimal discountAmount;//活动优惠金额
    private BigDecimal shopCarTotalAmount;//总金额
    private BigDecimal totalGoodsTaxAmount;//预计总税费
    private boolean ifShowDiscountAmount;//是否显示活动已减
    private List<CartResult> shopDtoList;//仓库列表

    public boolean isIfShowDiscountAmount() {
        return ifShowDiscountAmount;
    }

    public void setIfShowDiscountAmount(boolean ifShowDiscountAmount) {
        this.ifShowDiscountAmount = ifShowDiscountAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getShopCarTotalAmount() {
        return shopCarTotalAmount;
    }

    public void setShopCarTotalAmount(BigDecimal shopCarTotalAmount) {
        this.shopCarTotalAmount = shopCarTotalAmount;
    }

    public BigDecimal getTotalGoodsTaxAmount() {
        return totalGoodsTaxAmount;
    }

    public void setTotalGoodsTaxAmount(BigDecimal totalGoodsTaxAmount) {
        this.totalGoodsTaxAmount = totalGoodsTaxAmount;
    }

    public List<CartResult> getShopDtoList() {
        return shopDtoList;
    }

    public void setShopDtoList(List<CartResult> shopDtoList) {
        this.shopDtoList = shopDtoList;
    }
}
