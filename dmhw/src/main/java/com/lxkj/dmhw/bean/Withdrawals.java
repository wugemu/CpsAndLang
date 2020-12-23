package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取提现信息
 */
@Getter
@Setter
public class Withdrawals implements Serializable {
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
     * 可提现金额
     */
    private String withdrawalamount = "";

    /**
     * 说明
     */
    private String withdrawalexp = "";
    /**
     * 本月结算预估佣金
     */
    private String thismonthsettleamount = "";
    /**
     * 上月结算预估佣金
     */
    private String lastmonthsettleamount = "";

}
