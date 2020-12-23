package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2018/9/27.
 */

public class Product implements Serializable {
    private List<NewsBean> bannerImgs;//
    private List<NewsBean> detailImgs;//
    private List<NewsBean> naturalImgs;
    private List<Coupon> coupons;
    private List<ActivityPolicy> activitys;
    private GoodBean goodsDetail;
    private PostTipBean goodsPostageDTO;
    private List<GoodSku> goodsSkus;
    private List<Multiple> specificationList;
    private SecondKill seckillActivity;//秒杀信息
    private SecondKill specialPriceInfo;//特价信息
    private String buyInfoImgUrl;
    private boolean ifVipPutOn;
    private List<NewsBean> videoImgs;//
    private int ifUserRealVip;//1-表示是的，请展示新礼包详情页面 -1 用户非vip 2 用户已经是vip但还有订单未完结
    private String normalBuyInfoImgUrl;//普通商品的购买须知	礼包下单二次购买显示不同
    private double couponAmount;
    private String giftDes;
    private String aptitudeWeexUrl;
    private String updateVipTsImg;
    private Group groupGoodsDetailDto;
    private boolean ifInWithinBuyLib;//商品是否已在内购库中
    private boolean ifHavWithinBuyLibRight;//是否有加入内购会的权限
    private String liveId;
    private boolean ifNewUser;//是否新人
    private int newUserBuySate;//0-可以购买 1-当前商品已购买 2-当前商品已加购 3-其他商品已购买 4-其他商品已加购
    private boolean ifCanBuy=true;//是否能购买
    private boolean ifCanDirtBuy;//是否可以单独购买

    public boolean isIfCanDirtBuy() {
        return ifCanDirtBuy;
    }

    public void setIfCanDirtBuy(boolean ifCanDirtBuy) {
        this.ifCanDirtBuy = ifCanDirtBuy;
    }

    private List<SkuPrice> specialShoppePrices;//专柜商品价格信息

    public List<SkuPrice> getSpecialShoppePrices() {
        return specialShoppePrices;
    }

    public void setSpecialShoppePrices(List<SkuPrice> specialShoppePrices) {
        this.specialShoppePrices = specialShoppePrices;
    }

    public boolean isIfCanBuy() {
        return ifCanBuy;
    }

    public void setIfCanBuy(boolean ifCanBuy) {
        this.ifCanBuy = ifCanBuy;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public int getNewUserBuySate() {
        return newUserBuySate;
    }

    public void setNewUserBuySate(int newUserBuySate) {
        this.newUserBuySate = newUserBuySate;
    }

    public boolean isIfNewUser() {
        return ifNewUser;
    }

    public void setIfNewUser(boolean ifNewUser) {
        this.ifNewUser = ifNewUser;
    }


    public boolean isIfInWithinBuyLib() {
        return ifInWithinBuyLib;
    }

    public void setIfInWithinBuyLib(boolean ifInWithinBuyLib) {
        this.ifInWithinBuyLib = ifInWithinBuyLib;
    }

    public boolean isIfHavWithinBuyLibRight() {
        return ifHavWithinBuyLibRight;
    }

    public void setIfHavWithinBuyLibRight(boolean ifHavWithinBuyLibRight) {
        this.ifHavWithinBuyLibRight = ifHavWithinBuyLibRight;
    }

    public Group getGroupGoodsDetailDto() {
        return groupGoodsDetailDto;
    }

    public void setGroupGoodsDetailDto(Group groupGoodsDetailDto) {
        this.groupGoodsDetailDto = groupGoodsDetailDto;
    }
    public String getUpdateVipTsImg() {
        return updateVipTsImg;
    }

    public void setUpdateVipTsImg(String updateVipTsImg) {
        this.updateVipTsImg = updateVipTsImg;
    }

    public String getAptitudeWeexUrl() {
        return aptitudeWeexUrl;
    }

    public void setAptitudeWeexUrl(String aptitudeWeexUrl) {
        this.aptitudeWeexUrl = aptitudeWeexUrl;
    }

    public SecondKill getSpecialPriceInfo() {
        return specialPriceInfo;
    }

    public void setSpecialPriceInfo(SecondKill specialPriceInfo) {
        this.specialPriceInfo = specialPriceInfo;
    }

    public PostTipBean getGoodsPostageDTO() {
        return goodsPostageDTO;
    }

    public void setGoodsPostageDTO(PostTipBean goodsPostageDTO) {
        this.goodsPostageDTO = goodsPostageDTO;
    }

    public List<Multiple> getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(List<Multiple> specificationList) {
        this.specificationList = specificationList;
    }

    public String getGiftDes() {
        return giftDes;
    }

    public void setGiftDes(String giftDes) {
        this.giftDes = giftDes;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getIfUserRealVip() {
        return ifUserRealVip;
    }

    public void setIfUserRealVip(int ifUserRealVip) {
        this.ifUserRealVip = ifUserRealVip;
    }

    public String getNormalBuyInfoImgUrl() {
        return normalBuyInfoImgUrl;
    }

    public void setNormalBuyInfoImgUrl(String normalBuyInfoImgUrl) {
        this.normalBuyInfoImgUrl = normalBuyInfoImgUrl;
    }

    public List<NewsBean> getVideoImgs() {
        return videoImgs;
    }

    public void setVideoImgs(List<NewsBean> videoImgs) {
        this.videoImgs = videoImgs;
    }

    public List<NewsBean> getNaturalImgs() {
        return naturalImgs;
    }

    public void setNaturalImgs(List<NewsBean> naturalImgs) {
        this.naturalImgs = naturalImgs;
    }

    public boolean isIfVipPutOn() {
        return ifVipPutOn;
    }

    public void setIfVipPutOn(boolean ifVipPutOn) {
        this.ifVipPutOn = ifVipPutOn;
    }

    public String getBuyInfoImgUrl() {
        return buyInfoImgUrl;
    }

    public void setBuyInfoImgUrl(String buyInfoImgUrl) {
        this.buyInfoImgUrl = buyInfoImgUrl;
    }

    public List<NewsBean> getBannerImgs() {
        return bannerImgs;
    }

    public void setBannerImgs(List<NewsBean> bannerImgs) {
        this.bannerImgs = bannerImgs;
    }

    public List<NewsBean> getDetailImgs() {
        return detailImgs;
    }

    public void setDetailImgs(List<NewsBean> detailImgs) {
        this.detailImgs = detailImgs;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public List<ActivityPolicy> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<ActivityPolicy> activitys) {
        this.activitys = activitys;
    }

    public GoodBean getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(GoodBean goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public List<GoodSku> getGoodsSkus() {
        return goodsSkus;
    }

    public void setGoodsSkus(List<GoodSku> goodsSkus) {
        this.goodsSkus = goodsSkus;
    }

    public SecondKill getSeckillActivity() {
        return seckillActivity;
    }

    public void setSeckillActivity(SecondKill seckillActivity) {
        this.seckillActivity = seckillActivity;
    }
}
