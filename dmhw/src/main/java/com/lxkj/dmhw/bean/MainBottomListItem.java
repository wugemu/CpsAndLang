package com.lxkj.dmhw.bean;

import android.text.TextUtils;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.CommodityAdapterPJW290;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
/**
 * 首页底部 猜你喜欢实体
 * id	string	商品主键ID
 * name	string	名称
 * imageUrl	string	主图
 * sales	string	销量
 * price	string	券后价
 * cost	string	原价
 * coupon	string	券额
 * normalCommission	string	推广佣金
 * source	string	商品来源
 * sourceId	string	商品来源ID
 * cpsType	string	平台类型（code:类型编码，name:名称，logo:小图标）
 */
@Getter
@Setter
public class MainBottomListItem implements Serializable {


    /**
     * 商品ID
     */
    private String id = "";

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
     * pjw劵额
     */
    private String save = "";


    /**
     * 商品推广佣金
     */
    private String normalCommission = "";
    /**
     * 商品来源
     */
    private String source = "";
    /**
     * 商品来源ID
     */
    private String sourceId = "";

    /**
     * 商品优惠券
     */
    private String coupon = "";


    /**
     * 商品类型
     */
    private String cpsType = "";

    /**
     * 折扣
     */
    private String discount = "";
}
