package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户资料
 */
@Getter
@Setter
public class UserInfo {
    /**
     * 用户ID
     */
    private String userid = "";
    /**
     * 用户昵称
     */
    private String username = "";
    /**
     * 用户标识
     */
    private String usertype = "";
    /**
     * 手机号
     */
    private String userphone = "";
    /**
     * 头像
     */
    private String userpicurl = "";
    /**
     * 积分
     */
    private String userscore = "";
    /**
     * 金币
     */
    private String usergold = "";
    /**
     * 佣金
     */
    private String usercommission = "";
    /**
     * 邀请码
     */
    private String extensionid = "";
    /**
     * 商户号
     */
    private String merchantid = "";
    /**
     * 是否绑定微信
     */
    private String isbinding = "";
    /**
     * 自身绑定微信号
     */
    private String userwx = "";

    /**
     * 客服微信号
     */
    private String wxcode = "";
    /**
     * 升级店主弹窗  为空时弹窗
     */
    private String shopkeeperalert = "";

    /**
     * 是否设置比例
     */
    private String rate = "0";

    /**
     * 是否授权
     */
    private String isauth = "0";
    /**
     * 授权地址
     */
    private String authurl = "";
    /**
     * 用户身份
     */
    private String userLeverStr = "";
}
