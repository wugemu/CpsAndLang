package com.lxkj.dmhw.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 分享图片选择
 */
@Getter
@Setter
public class ShareCheck implements Serializable {
    /**
     * 图片地址
     */
    private String image = "";
    /**
     * 图片选中状态
     */
    private int Check = 0;

    /**
     * 图片选中为第一张是二维码推广图标志
     */
    private String isFirstQr = "0";

    //主图选中
    private int mainImageCheck=0;


    /**
     * 图片选中为第一张是二维码推广图标志
     */
    private String isMainFirstQr = "0";


    private int pos=0;

}
