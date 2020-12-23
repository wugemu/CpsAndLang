package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lxkj.dmhw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品详情290
 * id	string	商品主键ID
 * name	string	名称
 * imageUrl	string	主图
 * sales	string	销量
 * price	string	券后价
 * cost	string	原价
 * coupon	string	券额
 * normalCommission	string	推广佣金
 * upCommission	string	升级店主后推广佣金
 * source	string	商品来源
 * sourceId	string	商品来源ID
 * cpsType	string	平台类型（code:类型编码，name:名称，logo:小图标）
 * imageList	list	轮播图
 * desc	string	推荐理由
 * hasCoupon	boolean	是否有优惠券
 * couponInfo	json	优惠券信息
 * hasShop	boolean	是否有店铺信息
 * shopInfo	string	店铺信息
 */
@Getter
@Setter
public class CommodityDetails290 implements Serializable {
    /**
     * 商品ID
     */
    private String id = "";

    /**
     *平台类型
     */
    private String type = "";

    /**
     * 商品名称
     */
    private String name = "";

    /**
     * 商品主图
     */
    private String imageUrl = "";

    /**
     * 商品销量
     */
    private String sales = "";

    /**
     * 商品券后价
     */
    private String price = "";

    /**
     * 商品原价
     */
    private String cost = "";

    /**
     * 商品优惠券额
     */
    private String coupon = "";

    /**
     * 商品链接
     */
    private String url = "";

    /**
     * 折扣
     */
    private String discount = "";


    /**
     * 商品分享佣金
     */
    private String normalCommission = "";

    /**
     * 商品升级店主后推广佣金
     */
    private String upCommission = "";

    /**
     * 商品视频链接
     */
    private String  videoUrl = "";

    /**
     * 是否收藏
     */
    private String iscollection = "";

    /**
     * 收藏ID
     */
    private String collectid = "";
    /**
     * 商品来源
     */
    private String source = "";

    /**
     * 自购省
     */
    private String buySave = "";


    //限时抢购 是否开始
    private boolean allow=false;

    /**
     * 唯品会的直降
     */
    private String save = "";

    /**
     * 商品来源ID
     */
    private String sourceId = "";

    /**
     * 商品 推荐理由
     */
    private String desc = "";

    /**
     * 商品是否有优惠券
     */
    private Boolean hasCoupon = false;


    /**
     * 商品轮播图(jsonArray)
     */
    private String imageList = "";

    /**
     * 店铺信息(jsonObject)
     */
    private String shopInfo = "";

    /**
     * 商品类型(jsonObject)
     */
    private String cpsType = "";

    /**
     * 商品优惠券(jsonObject)
     */
    private String couponInfo = "";

    /**
     * 商品详情图
     */
    private String descImages = "";

    /**
     * 商品是否收藏
     */
    private Boolean isCollect = false;


    /**
     * 收藏 是否被选中
     */
    private boolean play = false;

    /**
     * 是否失效:未失效(true)失效(false)
     */
    private Boolean isEffective = true;

    /**
     * 获取短连接实体
     */
    private String cpsPromotionVo = "";

    //是否有比价
    private String isbj="";

    //pdd比价
    private Boolean isLowerPrice=false;

    //pdd比价规则地址
    private String lowerPriceRuleUrl="";

}
