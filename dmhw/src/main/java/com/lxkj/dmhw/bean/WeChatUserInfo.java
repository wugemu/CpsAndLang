package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信用户资料
 */
@Getter
@Setter
public class WeChatUserInfo implements Serializable {
    /**
     * 用户的标识
     */
    private String openid = "";
    /**
     * 用户昵称
     */
    private String nickname = "";
    /**
     * 用户性别
     */
    private String sex = "";
    /**
     * 省份
     */
    private String province = "";
    /**
     * 城市
     */
    private String city = "";
    /**
     * 国家
     */
    private String country = "";
    /**
     * 用户头像
     */
    private String headimgurl = "";
    /**
     * 用户统一标识
     */
    private String unionid = "";

}
