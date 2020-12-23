package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/9/27.
 */

public class ActivityPolicy implements Serializable {


    /**
     * amount : 10
     * couponPolicyName : 满2件减10元
     * endTime : 1535558399
     * id : 484363531471290400
     * startTime : 1533052800
     */

    private double amount;
    private String couponPolicyName;
    private long endTime;
    private long id;
    private long startTime;


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getCouponPolicyName() {
        return couponPolicyName;
    }

    public void setCouponPolicyName(String couponPolicyName) {
        this.couponPolicyName = couponPolicyName;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
