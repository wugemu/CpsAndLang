package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2018/9/22.
 */

public class Coupon implements Serializable {


    /**
     * couponCategory : 44643
     * topCategory : 1
     * amount : 11
     * couponId : 484401031942242300
     * couponName : 测试名称1
     * couponTag : 8
     * discount : 5
     * displayName : 2
     * endTime : 2018.08.09
     * startTime : 2018.08.07
     * status : 0
     * useOccasion : 0
     */
    private long id;//商品详情页可领取优惠券列表id
    private long couponId;//我的优惠券列表id
    private int couponCategory; //7_免单 8_免单指定商品 优惠券类别(1：满减券(满XX减XX),2：免税券,3：免单券,4：包邮券)
    private long topCategory;
    private double amount;
    private String couponName;
    private String couponTag;
    private double discount;
    private String displayName;
    private String endTime;
    private String startTime;
    private int status;
    private int useOccasion;//5_专柜优惠券
    private int state;//0-可领取 1-已领取（去使用）
    private int ifCanUse;//1-可以使用   0或字段不存在-不可以使用
    private String canUseTip;//不能使用的优惠券文案
    private int validCount;

    private int jumpType;//跳转类型(0-不跳转 1-主题场 2-活动)
    private String jumpUrl;//跳转地址
    private int type;//类型(0-优惠券 1-红包)

    public int getJumpType() {
        return jumpType;
    }

    public void setJumpType(int jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValidCount() {
        return validCount;
    }

    public void setValidCount(int validCount) {
        this.validCount = validCount;
    }

    public String getCanUseTip() {
        return canUseTip;
    }

    public void setCanUseTip(String canUseTip) {
        this.canUseTip = canUseTip;
    }

    public int getIfCanUse() {
        return ifCanUse;
    }

    public void setIfCanUse(int ifCanUse) {
        this.ifCanUse = ifCanUse;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCouponCategory() {
        return couponCategory;
    }

    public void setCouponCategory(int couponCategory) {
        this.couponCategory = couponCategory;
    }

    public long getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(long topCategory) {
        this.topCategory = topCategory;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponTag() {
        return couponTag;
    }

    public void setCouponTag(String couponTag) {
        this.couponTag = couponTag;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUseOccasion() {
        return useOccasion;
    }

    public void setUseOccasion(int useOccasion) {
        this.useOccasion = useOccasion;
    }


}
