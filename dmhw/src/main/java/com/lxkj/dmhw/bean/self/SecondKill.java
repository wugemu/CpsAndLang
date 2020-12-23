package com.lxkj.dmhw.bean.self;

/**
 * Created by lenovo on 2018/9/27.
 */

public class SecondKill {


    /**
     * activityId : 485046952250048500
     * buyStock : 300
     * endTime : 1537545599
     * maxPrice : 20
     * minPrice : 20
     * remainStock : -150
     * startTime : 1537495200
     * sumStock : 150
     */

    private long activityId;
    private int buyStock;
    private long endTime;
    private double maxPrice;
    private double minPrice;
    private int remainStock;
    private long startTime;
    private int sumStock;
    private double maxIncome;
    private double minIncome;
    private String timeDes;
    private String specialTag;//特价标签
    private int state;//特价状态 0-特价预热中 1-特价进行中
    private double profitAmount;//商品详情页赚的金额

    public double getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(double profitAmount) {
        this.profitAmount = profitAmount;
    }

    public String getSpecialTag() {
        return specialTag;
    }

    public void setSpecialTag(String specialTag) {
        this.specialTag = specialTag;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTimeDes() {
        return timeDes;
    }

    public void setTimeDes(String timeDes) {
        this.timeDes = timeDes;
    }

    public double getMaxIncome() {
        return maxIncome;
    }

    public void setMaxIncome(double maxIncome) {
        this.maxIncome = maxIncome;
    }

    public double getMinIncome() {
        return minIncome;
    }

    public void setMinIncome(double minIncome) {
        this.minIncome = minIncome;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public int getBuyStock() {
        return buyStock;
    }

    public void setBuyStock(int buyStock) {
        this.buyStock = buyStock;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public int getRemainStock() {
        return remainStock;
    }

    public void setRemainStock(int remainStock) {
        this.remainStock = remainStock;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getSumStock() {
        return sumStock;
    }

    public void setSumStock(int sumStock) {
        this.sumStock = sumStock;
    }
}
