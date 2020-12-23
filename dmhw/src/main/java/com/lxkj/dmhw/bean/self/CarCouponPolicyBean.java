package com.lxkj.dmhw.bean.self;

import java.util.List;

public class CarCouponPolicyBean {
    private long couponPolicyId;//优惠活动id
    private String couponPolicyName;//优惠活动名称
    private String couponPolicyTag;//活动标签
    private int reachState;//活动状态
    private String reachTitle;//活动提示文案
    private List<TradeGoodsCar> shopCarDtos;

    public long getCouponPolicyId() {
        return couponPolicyId;
    }

    public void setCouponPolicyId(long couponPolicyId) {
        this.couponPolicyId = couponPolicyId;
    }

    public String getCouponPolicyName() {
        return couponPolicyName;
    }

    public void setCouponPolicyName(String couponPolicyName) {
        this.couponPolicyName = couponPolicyName;
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

    public List<TradeGoodsCar> getShopCarDtos() {
        return shopCarDtos;
    }

    public void setShopCarDtos(List<TradeGoodsCar> shopCarDtos) {
        this.shopCarDtos = shopCarDtos;
    }
}
