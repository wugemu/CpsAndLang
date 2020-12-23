package com.lxkj.dmhw.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 运营推广
 */
@Getter
@Setter
public class NewsExam implements Serializable {
    /**
     * 申请时间
     */
    private String reqtime = "";
    /**
     * 申请用户id
     */
    private String msguserid = "";
    /**
     * 用户名
     */
    private String username = "";
    /**
     * 用户类型
     */
    private String usertype = "";
    /**
     * 手机号
     */
    private String userphone = "";
    /**
     * 创建时间
     */
    private String createtime = "";
    /**
     * 用户头像
     */
    private String userpicurl = "";
    /**
     * 团队人数
     */
    private String teamcnt = "";
    /**
     * 累计预估
     */
    private String profit = "";
    /**
     * 累计订单
     */
    private String ordercnt = "";
    /**
     * 邀请码
     */
    private String extensionid = "";
    /**
     * 微信号
     */
    private String wxcode = "";
    /**
     * 总佣金
     */
    private String commisionsum = "";
    /**
     * 总收益
     */
    private String profitsum = "";
    /**
     * 总贡献
     */
    private String contributionsum = "";
    /**
     * A级会员
     */
    private String acnt = "";
    /**
     * B级会员
     */
    private String bcnt = "";
    /**
     * 普通会员
     */
    private String normcnt = "";
    /**
     * 本日预估
     */
    private String todayamount = "";
    /**
     * 昨日预估
     */
    private String yesterdayamount = "";
    /**
     * 七日预估
     */
    private String sevenamount = "";
    /**
     * 本月预估
     */
    private String preamount = "";
    /**
     * 上月预估
     */
    private String monthamount = "";
    /**
     * 本日订单
     */
    private String todayordercnt = "";
    /**
     * 上月订单
     */
    private String preordercnt = "";
    /**
     * 本月订单
     */
    private String monthordercnt = "";
}
