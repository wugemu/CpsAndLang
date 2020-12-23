package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class CouponDialogBean implements Serializable {
    private Coupon coupon;
    private long couponId;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }
}
