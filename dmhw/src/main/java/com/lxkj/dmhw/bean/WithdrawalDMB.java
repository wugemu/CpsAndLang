package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 呆萌币提现
 */
@Getter
@Setter
public class WithdrawalDMB implements Serializable {
    /**
     * 支付宝账户
     */
    private String alipayacount = "";
    /**
     * 支付宝名称
     */
    private String alipayname = "";
    /**
     * 提现最低金额
     */
    private String withdrawallimit = "";
    /**
     * 提现最大金额
     */
    private String withdrawalmax = "";
    /**
     * 提现说明
     */
    private String withdrawaldesc = "";
    /**
     * 待结转说明
     */
    private String frozenmnyesc = "";

    /**
     * 待结转金额
     */
    private String mny = "";

    /**
     * 可提现金额
     */
    private String scoremny = "";

}
