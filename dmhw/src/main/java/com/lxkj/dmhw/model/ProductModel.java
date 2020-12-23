package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.bean.self.Group;
import com.lxkj.dmhw.bean.self.GroupUser;
import com.lxkj.dmhw.bean.self.Info;
import com.lxkj.dmhw.bean.self.Product;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.bean.self.SucaiBean;
import com.lxkj.dmhw.bean.self.ZgCartInfo;
import com.lxkj.dmhw.bean.self.ZgCartList;

import java.util.List;

/**
 * Created by lenovo on 2018/9/27.
 */

public class ProductModel extends BaseLangViewModel {
    private Product product;
    private Integer cartCount;
    private ShareBean shareBean;
    private boolean isCollection;
    private List<Coupon> couponList;
    private String couponId;
    private Product gift;
    private List<Info> infos;
    private String notifyPhone;
    private String supplierAptitude;
    private List<Group> joinGroupList;
    private List<Group> allGroupList;
    private List<GroupUser> groupUserList;
    private SucaiBean sucaiBean;
    private ZgCartList zgCarList;
    private ZgCartInfo zgCartInfo;

    public ZgCartInfo getZgCartInfo() {
        return zgCartInfo;
    }

    public void setZgCartInfo(ZgCartInfo zgCartInfo) {
        this.zgCartInfo = zgCartInfo;
    }

    public ZgCartList getZgCarList() {
        return zgCarList;
    }

    public void setZgCarList(ZgCartList zgCarList) {
        this.zgCarList = zgCarList;
    }

    public SucaiBean getSucaiBean() {
        return sucaiBean;
    }

    public void setSucaiBean(SucaiBean sucaiBean) {
        this.sucaiBean = sucaiBean;
    }

    public List<GroupUser> getGroupUserList() {
        return groupUserList;
    }

    public void setGroupUserList(List<GroupUser> groupUserList) {
        this.groupUserList = groupUserList;
    }

    public List<Group> getAllGroupList() {
        return allGroupList;
    }

    public void setAllGroupList(List<Group> allGroupList) {
        this.allGroupList = allGroupList;
    }

    public List<Group> getJoinGroupList() {
        return joinGroupList;
    }

    public void setJoinGroupList(List<Group> joinGroupList) {
        this.joinGroupList = joinGroupList;
    }

    public String getSupplierAptitude() {
        return supplierAptitude;
    }

    public void setSupplierAptitude(String supplierAptitude) {
        this.supplierAptitude = supplierAptitude;
    }

    public String getNotifyPhone() {
        return notifyPhone;
    }

    public void setNotifyPhone(String notifyPhone) {
        this.notifyPhone = notifyPhone;
    }

    public List<Info> getInfos() {
        return infos;
    }

    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }

    public Product getGift() {
        return gift;
    }

    public void setGift(Product gift) {
        this.gift = gift;
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

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public ShareBean getShareBean() {
        return shareBean;
    }

    public void setShareBean(ShareBean shareBean) {
        this.shareBean = shareBean;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
