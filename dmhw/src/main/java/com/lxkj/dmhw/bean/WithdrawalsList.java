package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 提现记录
 */
@Getter
@Setter
public class WithdrawalsList {
    /**
     * ID
     */
    private String withdrawalsid = "";
    /**
     * 时间
     */
    private String withdrawalstime = "";
    /**
     * 金额
     */
    private String withdrawalsmoney = "";
    /**
     * 状态
     */
    private String withdrawalsstatus = "";

    /**
     * 状态名称
     */
    private String name = "";
}
