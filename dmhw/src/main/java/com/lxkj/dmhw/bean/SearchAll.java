package com.lxkj.dmhw.bean;

import android.text.TextUtils;
import android.util.Log;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mtopsdk.mtop.intf.Mtop;

/**
 * 搜索全网
 */
@Getter
@Setter
public class SearchAll implements Serializable {
    /**
     * 淘宝商品ID
     */
    private String numIid = "";
    /**
     * 商品名称
     */
    private String title = "";
    /**
     * itemid 全网搜的id
     */
    private String itemId = "";
    /**
     * 商品短标题
     */
    private String shortTitle = "";
    /**
     * 商品图片
     */
    private String pictUrl = "";
    /**
     * 优惠券
     */
    private String couponInfo = "";
    /**
     * 原价
     */
    private String zkFinalPrice = "";
    /**
     * 店铺名称
     */
    private String shopTitle = "";

    /**
     * 店铺链接
     */
    private String couponShareUrl = "";
    /**
     * 平台类型:天猫(1)or淘宝（0）
     */
    private String userType = "";
    /**
     * 佣金比例
     */
    private String commissionRate = "";


    /**
     * 优惠券开始时间
     */
    private String couponStartTime = "";
    /**
     * 优惠券结束时间
     */
    private String couponEndTime = "";
    /**
     * 优惠券ID
     */
    private String couponId = "";
    /**
     * 销量
     */
    private int volume = 0;
    /**
     * 优惠券额度
     */
    private String couponAmount ="0";

    /**
     * 商品描述
     */
    private String  itemDescription ="";

    /**
     * 图片数据
     */
    private ArrayList<String> smallImages = new ArrayList<>();

    /**
     * 平台类型
     * @return 天猫(true)淘宝(false)
     */
    public boolean isCheck() {
        return userType.equals("1");
    }

    /**
     * @return 优惠券额度
     */
    public String getDiscount() {
        String discount="0";
        if (TextUtils.isEmpty(couponInfo)){
           return "0";
        }else{
            if (couponInfo.substring(couponInfo.indexOf("满") +1, couponInfo.indexOf("减")).contains("元")){
                discount = couponInfo.substring(couponInfo.indexOf("减") +1, couponInfo.length()-1);
            }else{
                discount= couponInfo.substring(couponInfo.indexOf("满") +1, couponInfo.indexOf("减"));
            }
        }
        return discount;
    }

    /**
     * @return 券后价
     */
    public String getMoney() {
        return new DecimalFormat("0.0").format(Utils.getFloat(Float.parseFloat(zkFinalPrice) - Float.parseFloat(couponAmount+""))) + "";
    }

    /**
     * @return 预估佣金
     */
    public String getEstimate() {
        return !Objects.equals((DateStorage.getInformation()).getUsertype(), "3") ? Utils.getFloat(Float.parseFloat(getMoney()) * (Float.parseFloat(commissionRate) / 10000) * (Float.parseFloat(Variable.ration) / 100)) + "" : "";
    }

    /**
     * @return 商品名
     */
    public String getShopName() {
        return shortTitle.equals("") ? title : shortTitle;
    }

}
