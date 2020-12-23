package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 下级代理数量
 */
@Getter
@Setter
public class AgentQuantity {
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
}
