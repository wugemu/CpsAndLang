package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.self.CartResult;
import com.lxkj.dmhw.bean.self.CartResultNewBean;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.bean.self.TopNotifyBean;
import com.lxkj.dmhw.bean.self.TradeGoodsCar;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/9/28.
 */

public class CartModel extends BaseLangViewModel {
    private List<TradeGoodsCar> invalidList;
    private List<CartResult> cartList;
    private CartResultNewBean cartResultNewBean;
    private TopNotifyBean info;
    private TradeGoodsCar car;
    private int changeNum;
    private int invalidCount;
    private List<Coupon> couponList;
    private String couponId;
    private boolean ifUseInterface;//是否使用接口返回的领券状态

    public boolean isIfUseInterface() {
        return ifUseInterface;
    }

    public void setIfUseInterface(boolean ifUseInterface) {
        this.ifUseInterface = ifUseInterface;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public CartResultNewBean getCartResultNewBean() {
        return cartResultNewBean;
    }

    public void setCartResultNewBean(CartResultNewBean cartResultNewBean) {
        this.cartResultNewBean = cartResultNewBean;
    }

    public int getInvalidCount() {
        return invalidCount;
    }

    public void setInvalidCount(int invalidCount) {
        this.invalidCount = invalidCount;
    }

    public int getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(int changeNum) {
        this.changeNum = changeNum;
    }

    public TradeGoodsCar getCar() {
        return car;
    }

    public void setCar(TradeGoodsCar car) {
        this.car = car;
    }

    public TopNotifyBean getInfo() {
        return info;
    }

    public void setInfo(TopNotifyBean info) {
        this.info = info;
    }

    public List<TradeGoodsCar> getInvalidList() {
        return invalidList;
    }

    public void setInvalidList(List<TradeGoodsCar> invalidList) {
        this.invalidList = invalidList;
    }

    public List<CartResult> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartResult> cartList) {
        this.cartList = cartList;
    }
}
