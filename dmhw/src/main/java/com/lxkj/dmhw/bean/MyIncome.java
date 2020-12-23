package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 *我的收益
 */
@Getter
@Setter
public class MyIncome {
    /**
     * 可提现
     */
    private String usercommission = "";
    /**
     * 总金额
     */
    private String accumulatedincome = "";
}
