package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 1 on 2017/2/28.
 */

public class CartResult implements Serializable {
    private boolean ifCanReceiveCoupon;//是否显示领券
    private List<CarCouponPolicyBean> shopCarCouponPolicys;//优惠活动list
    private String warehouseName;//发货仓名
    private String deliveryType;//发货类型
    private List<TradeGoodsCar> shopCarDtos;//最终转换的数据

    public CartResult() {
    }

    public List<TradeGoodsCar> getShopCarDtos() {
        return shopCarDtos;
    }

    public void setShopCarDtos(List<TradeGoodsCar> shopCarDtos) {
        this.shopCarDtos = shopCarDtos;
    }

    public boolean isIfCanReceiveCoupon() {
        return ifCanReceiveCoupon;
    }

    public void setIfCanReceiveCoupon(boolean ifCanReceiveCoupon) {
        this.ifCanReceiveCoupon = ifCanReceiveCoupon;
    }

    public List<CarCouponPolicyBean> getShopCarCouponPolicys() {
        return shopCarCouponPolicys;
    }

    public void setShopCarCouponPolicys(List<CarCouponPolicyBean> shopCarCouponPolicys) {
        this.shopCarCouponPolicys = shopCarCouponPolicys;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
}
