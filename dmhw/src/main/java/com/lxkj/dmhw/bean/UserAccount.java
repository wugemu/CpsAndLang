package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户成交记录
 */
@Getter
@Setter
public class UserAccount {
    /**
     * 今日预估
     */
    private String todayincome = "";
    /**
     * 本月预估佣金
     */
    private String thismonthincome = "";
    /**
     * 上月预估佣金
     */
    private String lastmonthincome = "";

    /**
     * 可提现
     */
    private String usercommission = "";
    /**
     * 累计收益
     */
    private String accumulatedincome = "";
    /**
     * 待到账
     */
    private String waitsettleamount ="";
}
