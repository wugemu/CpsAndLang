package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 签到
 */
@Getter
@Setter
public class Sign implements Serializable {
    /**
     * 签到天数
     */
    private String continuitydays = "";
    /**
     * 用户积分
     */
    private String usergold = "";
    /**
     * 本次积分
     */
    private String gold = "";
    /**
     * 下次积分
     */
    private String nextgold = "";
    /**
     * 基础积分
     */
    private String basegold = "";
    /**
     * 连续7天积分
     */
    private String rewardgold = "";

}
