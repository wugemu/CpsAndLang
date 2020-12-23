package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.self.AddrBean;
import com.lxkj.dmhw.bean.self.AmountSettleBean;
import com.lxkj.dmhw.bean.self.CouponSettleBean;
import com.lxkj.dmhw.bean.self.CreateOrderBean;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.bean.self.PhoneLTBean;
import com.lxkj.dmhw.bean.self.PhoneLTDetailBean;
import com.lxkj.dmhw.bean.self.TaxDetailBean;
import com.lxkj.dmhw.bean.self.TopNotifyBean;
import com.lxkj.dmhw.bean.self.UserAccount;
import com.lxkj.dmhw.bean.self.WarehouseBean;
import com.lxkj.dmhw.bean.self.WxPrePayBean;

import java.math.BigDecimal;
import java.util.List;

public class PayModel extends BaseLangViewModel {
    private List<WarehouseBean> skuList;
    private AddrBean userAddr;
    private UserAccount userIdcard;
    private boolean ifNeedCardId=true;//是否需要身份证（实名认证） 默认实名认证
    private boolean ifNeedShowServiceAgreement;//是否需要服务协议

    private String couponNum;//可用优惠券数量
    private TopNotifyBean topNotifyBean;//头部通知信息
    private CouponSettleBean couponSettleBean;//优惠券列表
    private AmountSettleBean amountSettleBean;//确认订单页面金额
    private CreateOrderBean createOrderBean;//创建订单结果
    private String payResult;//支付参数

    //礼包确认订单页使用字段
    private double couponAmount;//反优惠券总额
    private String giftDes;//优惠券返还说明;
    private String returnTitle;//返券标签
    private double giftRebateAmount;//兑换礼包可抵扣金额
    private double realPayAmount;//实付款
    private double sumAmount;//商品金额
    private double sumTax;//税费
    private double sumPostage=0.0;//邮费
    private double totalAmount;//小计
    private String balancePayMsg;//不能使用余额的原因
    private int balanceType;//1-礼包余额 2-现金余额
    private boolean isCanUseCash;//是否能使用余额
    private String bottomPrompt;//底部提示信息
    private boolean ifLiantongCard;//是否是电话卡需要填写身份资料信息 true表示是 没有返回或者返回false表示不是
    private List<PhoneLTBean> phoneLTBeanList;
    private PhoneLTBean selectPhoneLTBean;
    private PhoneLTDetailBean phoneLTDetailBean;
    private String upImgUrl;
    private List<TaxDetailBean> taxDetails;//税费说明明显
    private double score;//获得得积分
    private List<GoodBean> orderDelayGoodsReminderList;
    private WxPrePayBean wxPrePayBean;//微信小程序支付
    private String payResultStr;//自己的接口订单支付结果
    private List<String> groupUserInfo;//团购成员list
    private boolean isEqualAddress;//是否相同地址
    private String addressContent;
    private String changeTradeNo;//一键开团 修改后的订单号
    private boolean isEqualAddressShow;//团长开团是否显示集货团true显示

    private String addressPop;//集货地址团窗文案
    private String freePop;//免费团弹窗文案
    private boolean isFreeReturn;//是否有免费团返点


    private BigDecimal returnAbleCashAmount;//返现金额
    private String returnCashTitle;//返现文案
    private boolean ifReturnCash;//是否显示返现

    public BigDecimal getReturnAbleCashAmount() {
        return returnAbleCashAmount;
    }

    public void setReturnAbleCashAmount(BigDecimal returnAbleCashAmount) {
        this.returnAbleCashAmount = returnAbleCashAmount;
    }

    public String getReturnCashTitle() {
        return returnCashTitle;
    }

    public void setReturnCashTitle(String returnCashTitle) {
        this.returnCashTitle = returnCashTitle;
    }

    public boolean isIfReturnCash() {
        return ifReturnCash;
    }

    public void setIfReturnCash(boolean ifReturnCash) {
        this.ifReturnCash = ifReturnCash;
    }

    public String getAddressPop() {
        return addressPop;
    }

    public void setAddressPop(String addressPop) {
        this.addressPop = addressPop;
    }

    public String getFreePop() {
        return freePop;
    }

    public void setFreePop(String freePop) {
        this.freePop = freePop;
    }

    public boolean isFreeReturn() {
        return isFreeReturn;
    }

    public void setFreeReturn(boolean freeReturn) {
        isFreeReturn = freeReturn;
    }

    public boolean isEqualAddressShow() {
        return isEqualAddressShow;
    }

    public void setEqualAddressShow(boolean equalAddressShow) {
        isEqualAddressShow = equalAddressShow;
    }

    public String getChangeTradeNo() {
        return changeTradeNo;
    }

    public void setChangeTradeNo(String changeTradeNo) {
        this.changeTradeNo = changeTradeNo;
    }

    public String getAddressContent() {
        return addressContent;
    }

    public void setAddressContent(String addressContent) {
        this.addressContent = addressContent;
    }

    public boolean isEqualAddress() {
        return isEqualAddress;
    }

    public void setEqualAddress(boolean equalAddress) {
        isEqualAddress = equalAddress;
    }

    public List<String> getGroupUserInfo() {
        return groupUserInfo;
    }

    public void setGroupUserInfo(List<String> groupUserInfo) {
        this.groupUserInfo = groupUserInfo;
    }

    public String getPayResultStr() {
        return payResultStr;
    }

    public void setPayResultStr(String payResultStr) {
        this.payResultStr = payResultStr;
    }

    public WxPrePayBean getWxPrePayBean() {
        return wxPrePayBean;
    }

    public void setWxPrePayBean(WxPrePayBean wxPrePayBean) {
        this.wxPrePayBean = wxPrePayBean;
    }

    public List<GoodBean> getOrderDelayGoodsReminderList() {
        return orderDelayGoodsReminderList;
    }

    public void setOrderDelayGoodsReminderList(List<GoodBean> orderDelayGoodsReminderList) {
        this.orderDelayGoodsReminderList = orderDelayGoodsReminderList;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isIfNeedShowServiceAgreement() {
        return ifNeedShowServiceAgreement;
    }

    public void setIfNeedShowServiceAgreement(boolean ifNeedShowServiceAgreement) {
        this.ifNeedShowServiceAgreement = ifNeedShowServiceAgreement;
    }

    public List<TaxDetailBean> getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(List<TaxDetailBean> taxDetails) {
        this.taxDetails = taxDetails;
    }

    public double getSumPostage() {
        return sumPostage;
    }

    public void setSumPostage(double sumPostage) {
        this.sumPostage = sumPostage;
    }

    public String getUpImgUrl() {
        return upImgUrl;
    }

    public void setUpImgUrl(String upImgUrl) {
        this.upImgUrl = upImgUrl;
    }

    public PhoneLTDetailBean getPhoneLTDetailBean() {
        return phoneLTDetailBean;
    }

    public void setPhoneLTDetailBean(PhoneLTDetailBean phoneLTDetailBean) {
        this.phoneLTDetailBean = phoneLTDetailBean;
    }

    public PhoneLTBean getSelectPhoneLTBean() {
        return selectPhoneLTBean;
    }

    public void setSelectPhoneLTBean(PhoneLTBean selectPhoneLTBean) {
        this.selectPhoneLTBean = selectPhoneLTBean;
    }

    public List<PhoneLTBean> getPhoneLTBeanList() {
        return phoneLTBeanList;
    }

    public void setPhoneLTBeanList(List<PhoneLTBean> phoneLTBeanList) {
        this.phoneLTBeanList = phoneLTBeanList;
    }

    public boolean isIfLiantongCard() {
        return ifLiantongCard;
    }

    public void setIfLiantongCard(boolean ifLiantongCard) {
        this.ifLiantongCard = ifLiantongCard;
    }

    public String getBalancePayMsg() {
        return balancePayMsg;
    }

    public void setBalancePayMsg(String balancePayMsg) {
        this.balancePayMsg = balancePayMsg;
    }

    public int getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(int balanceType) {
        this.balanceType = balanceType;
    }

    public String getBottomPrompt() {
        return bottomPrompt;
    }

    public void setBottomPrompt(String bottomPrompt) {
        this.bottomPrompt = bottomPrompt;
    }

    public boolean isCanUseCash() {
        return isCanUseCash;
    }

    public void setCanUseCash(boolean canUseCash) {
        isCanUseCash = canUseCash;
    }

    public boolean isIfNeedCardId() {
        return ifNeedCardId;
    }

    public void setIfNeedCardId(boolean ifNeedCardId) {
        this.ifNeedCardId = ifNeedCardId;
    }

    public String getReturnTitle() {
        return returnTitle;
    }

    public void setReturnTitle(String returnTitle) {
        this.returnTitle = returnTitle;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getGiftDes() {
        return giftDes;
    }

    public void setGiftDes(String giftDes) {
        this.giftDes = giftDes;
    }

    public double getGiftRebateAmount() {
        return giftRebateAmount;
    }

    public void setGiftRebateAmount(double giftRebateAmount) {
        this.giftRebateAmount = giftRebateAmount;
    }

    public double getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(double realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    public CreateOrderBean getCreateOrderBean() {
        return createOrderBean;
    }

    public void setCreateOrderBean(CreateOrderBean createOrderBean) {
        this.createOrderBean = createOrderBean;
    }

    public TopNotifyBean getTopNotifyBean() {
        return topNotifyBean;
    }

    public void setTopNotifyBean(TopNotifyBean topNotifyBean) {
        this.topNotifyBean = topNotifyBean;
    }

    public AmountSettleBean getAmountSettleBean() {
        return amountSettleBean;
    }

    public void setAmountSettleBean(AmountSettleBean amountSettleBean) {
        this.amountSettleBean = amountSettleBean;
    }

    public CouponSettleBean getCouponSettleBean() {
        return couponSettleBean;
    }

    public void setCouponSettleBean(CouponSettleBean couponSettleBean) {
        this.couponSettleBean = couponSettleBean;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    public List<WarehouseBean> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<WarehouseBean> skuList) {
        this.skuList = skuList;
    }

    public AddrBean getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(AddrBean userAddr) {
        this.userAddr = userAddr;
    }

    public UserAccount getUserIdcard() {
        return userIdcard;
    }

    public void setUserIdcard(UserAccount userIdcard) {
        this.userIdcard = userIdcard;
    }

    public double getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(double sumAmount) {
        this.sumAmount = sumAmount;
    }

    public double getSumTax() {
        return sumTax;
    }

    public void setSumTax(double sumTax) {
        this.sumTax = sumTax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
