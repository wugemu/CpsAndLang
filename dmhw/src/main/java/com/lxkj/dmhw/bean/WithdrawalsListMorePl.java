package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 提现记录
 */
@Getter
@Setter
public class WithdrawalsListMorePl {
    /**
     * ID
     */
    private String id = "";
    /**
     * 时间
     */
    private String createDate = "";
    /**
     * 金额
     */
    private String mny = "";
    /**
     * 状态
     */
    private String stateName = "";
}
