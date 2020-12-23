package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class DeclarationDetailBean implements Serializable {
    private String idCard;
    private int isUpdateFlag;//0-未修改 1-修改
    private String payer;
    private String tradeNo;
    private int tradeStatus;
    private int type;//1-支付宝 2-微信
    private List<OrderGoodBean> tradeGoodsList;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getIsUpdateFlag() {
        return isUpdateFlag;
    }

    public void setIsUpdateFlag(int isUpdateFlag) {
        this.isUpdateFlag = isUpdateFlag;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<OrderGoodBean> getTradeGoodsList() {
        return tradeGoodsList;
    }

    public void setTradeGoodsList(List<OrderGoodBean> tradeGoodsList) {
        this.tradeGoodsList = tradeGoodsList;
    }
}
