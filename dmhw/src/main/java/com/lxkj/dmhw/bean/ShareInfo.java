package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 分享商品信息
 */
@Getter
@Setter
public class ShareInfo implements Serializable {
    /**
     * 商品名称
     */
    private String name = "";
    /**
     * 销量
     */
    private String sales = "";
    /**
     * 价格
     */
    private String money = "";
    /**
     * 优惠券
     */
    private String discount = "";
    /**
     * 短链接
     */
    private String shortLink = "";
    /**
     * 推荐理由
     */
    private String recommended = "";

    /**
     * 推荐理由2
     */
    private String content = "";

    /**
     * 佣金
     */
    private String commission = "";


    /**
     * 原价
     */
    private String shopprice = "";

    /**
     * 平台类型:天猫(true)淘宝(false)
     */
    private boolean check = false;

    /**
     * CPS平台
     */
    private String type = "";

    /**
     * 平台logo
     */
    private String logo= "";

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.name);
//        dest.writeString(this.sales);
//        dest.writeString(this.money);
//        dest.writeString(this.shopprice);
//        dest.writeString(this.discount);
//        dest.writeString(this.shortLink);
//        dest.writeString(this.recommended);
//        dest.writeString(this.content);
//        dest.writeString(this.type);
//        dest.writeByte(this.check ? (byte) 1 : (byte) 0);
//    }
//
//    public ShareInfo() {
//    }
//
//    protected ShareInfo(Parcel in) {
//        this.name = in.readString();
//        this.sales = in.readString();
//        this.money = in.readString();
//        this.shopprice = in.readString();
//        this.discount = in.readString();
//        this.shortLink = in.readString();
//        this.recommended = in.readString();
//        this.content=in.readString();
//        this.type=in.readString();
//        this.check = in.readByte() != 0;
//    }
//
//    public static final Parcelable.Creator<ShareInfo> CREATOR = new Parcelable.Creator<ShareInfo>() {
//        @Override
//        public ShareInfo createFromParcel(Parcel source) {
//            return new ShareInfo(source);
//        }
//
//        @Override
//        public ShareInfo[] newArray(int size) {
//            return new ShareInfo[size];
//        }
//    };
}
