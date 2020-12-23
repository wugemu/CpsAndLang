package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 团队数据
 */
@Getter
@Setter
public class TeamUpdataInfo {

    /**
     * 用户ID
     */
    private String userid="";
    /**
     * 用户类型
     */
    private String usertype="";

    /**
     * 手机号
     */
    private String userphone="";
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
