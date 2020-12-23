package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class CouponSettleBean implements Serializable{
    private List<Coupon> canUse;
    private List<Coupon> cannotUse;

    public List<Coupon> getCanUse() {
        return canUse;
    }

    public void setCanUse(List<Coupon> canUse) {
        this.canUse = canUse;
    }

    public List<Coupon> getCannotUse() {
        return cannotUse;
    }

    public void setCannotUse(List<Coupon> cannotUse) {
        this.cannotUse = cannotUse;
    }
}
