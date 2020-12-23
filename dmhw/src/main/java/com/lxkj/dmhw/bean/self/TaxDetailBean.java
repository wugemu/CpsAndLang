package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class TaxDetailBean implements Serializable {
    private double taxAmount;
    private String taxTitle;

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTaxTitle() {
        return taxTitle;
    }

    public void setTaxTitle(String taxTitle) {
        this.taxTitle = taxTitle;
    }
}
