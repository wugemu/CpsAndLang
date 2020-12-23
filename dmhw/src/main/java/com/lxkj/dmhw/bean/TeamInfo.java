package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 团队数据
 */
@Getter
@Setter
public class TeamInfo {
    /**
     * A级代理
     */
    private String ausercount = "";
    /**
     * B级代理
     */
    private String busercount = "";
    /**
     * 粉丝数量
     */
    private String fusercount = "";
    /**
     * 昨日邀请数量
     */
    private String ydinviter = "";
    /**
     * 今日邀请人数
     */
    private String tdinviter = "";
    /**
     * 昵称
     */
    private String username = "";
    /**
     * 头像
     */
    private String userpicurl = "";
    /**
     * 微信号
     */
    private String wxcode = "";
    /**
     * 邀请码
     */
    private String extensionid = "";
}
