package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class WxPrePayBean implements Serializable {
    private String miniProgramPath;//小程序支付路由
    private String wxUserName;
    private boolean ifMinProgramPay;//是否使用小程序支付路由

    public String getMiniProgramPath() {
        return miniProgramPath;
    }

    public void setMiniProgramPath(String miniProgramPath) {
        this.miniProgramPath = miniProgramPath;
    }

    public String getWxUserName() {
        return wxUserName;
    }

    public void setWxUserName(String wxUserName) {
        this.wxUserName = wxUserName;
    }

    public boolean isIfMinProgramPay() {
        return ifMinProgramPay;
    }

    public void setIfMinProgramPay(boolean ifMinProgramPay) {
        this.ifMinProgramPay = ifMinProgramPay;
    }
}
