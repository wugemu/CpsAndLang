package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 升级合伙人条件
 */
@Getter
@Setter
public class PartnerUpgrade {
    /**
     * 下级加盟商数量
     */
    private String franchisercnt = "";
    /**
     * 说明
     */
    private String activitydesc = "";
}
