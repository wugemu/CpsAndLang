package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 升级加盟商条件
 */
@Getter
@Setter
public class FranchiserUpgrade {
    /**
     * A级代理数量
     */
    private String aagentcnt = "";
    /**
     * B级代理数量
     */
    private String bagentcnt = "";
    /**
     * 当月收入
     */
    private String currentamount = "";
    /**
     * 活动说明
     */
    private String activitydesc = "";
}
