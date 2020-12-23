package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lxkj.dmhw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品详情
 */
@Getter
@Setter
public class CommodityDetails implements Serializable {
    /**
     * 商品ID
     */
    private String id = "";
    /**
     * 淘宝商品ID
     */
    private String shopid = "";
    /**
     * 优惠券ID
     */
    private String couponid = "";
    /**
     * 收藏ID
     */
    private String collectid = "";
    /**
     * 原价
     */
    private String shopprice = "";
    /**
     * 销量
     */
    private String shopmonthlysales = "";
    /**
     * 商品名称
     */
    private String shopname = "";
    /**
     * 商品短标题
     */
    private String shortname = "";
    /**
     * 预估佣金
     */
    private String precommission = "";
    /**
     * 优惠券起始时间
     */
    private String couponstart = "";
    /**
     * 平台类型:天猫or淘宝
     */
    private String platformtype = "";
    /**
     * 是否抢购
     */
    private String isallow = "";
    /**
     * 优惠券结束时间
     */
    private String couponend = "";
    /**
     * 一级ID
     */
    private String shopclassone = "";
    /**
     * 二级ID
     */
    private String shopclasstwo = "";
    /**
     * 优惠券推广链接
     */
    private String couponpromotionlink = "";
    /**
     * 推荐理由
     */
    private String recommended = "";
    /**
     * 短链接
     */
    private String shareshortlink = "";
    /**
     * 视频地址
     */
    private String videolink = "";
    /**
     * 是否收藏
     */
    private String iscollection = "";
    /**
     * 店主预估佣金
     */
    private String superprecommission = "";
    /**
     * 优惠券
     */
    private float coupondenomination = 0;

    /**
     * 进入店铺链接
     */
    private String sellerlink ="";

    /**
     * 分享页面编辑文案的佣金
     */
    private String commission ="";

    /**
     * 单图
     */
    private String pictUrl ="";

    /**
     * 商品详情轮播图
     */
    private ArrayList<CommodityImageUrl> shoppiclist = new ArrayList<>();

    /**
     * 平台类型
     * @return 天猫(true)淘宝(false)
     */
    public boolean isCheck() {
        return platformtype.equals("天猫");
    }

    /**
     * 是否抢购
     * @return 允许(true)不允许(false)
     */
    public boolean isAllow() {
        return isallow.equals("1");
    }

    /**
     * @return 券后价
     */
    public String getMoney() {
        return Utils.getFloat(Float.parseFloat(shopprice) - coupondenomination) + "";
    }

    /**
     * @return 商品名称
     */
    public String getTitle() {
        return shortname.equals("") ? shopname : shortname;
    }

    /**
     * @return 优惠券
     */
    public String getDiscount() {
        return (int) coupondenomination + "";
    }

    /**
     * 商品详情轮播图
     */
    @Getter
    @Setter
    public static class CommodityImageUrl implements Serializable {
        /**
         * 商品图片
         */
        private String shoppicurl = "";

    }

}
