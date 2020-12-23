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
public class WithdrawalsMorePl implements Serializable {
    /**
     * 支付宝账户
     */
    private String alipayAccount = "";
    /**
     * 支付宝名称
     */
    private String alipayName = "";
    /**
     * 提现最低金额
     */
    private String min = "";
    /**
     * 提现最高金额
     */
    private String max = "";
    /**
     * 可提现金额
     */
    private String availableAmt = "";

    /**
     * 说明
     */
    private String desc = "";

}
