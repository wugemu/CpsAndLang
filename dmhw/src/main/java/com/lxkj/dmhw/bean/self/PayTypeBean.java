package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class PayTypeBean implements Serializable {
    private String title;
    private int type;//1微信 2支付宝
    private boolean enabled;//true表示可用，false表示不可用
    private String accounTholder;
    private String accountNumber;
    private String accountNumberReal;//真实的银行卡号
    private int cardType;//绑定类型 0:支付宝;1:微信;2:银行储蓄卡
    private long id;
    private String bankName;//银行名字
    private int bindStatus;// 银行卡绑定状态 0未绑定 1已绑定未电签 2已绑定待审核 3已绑定已电签

    public String getAccountNumberReal() {
        return accountNumberReal;
    }

    public void setAccountNumberReal(String accountNumberReal) {
        this.accountNumberReal = accountNumberReal;
    }

    public int getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(int bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccounTholder() {
        return accounTholder;
    }

    public void setAccounTholder(String accounTholder) {
        this.accounTholder = accounTholder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
