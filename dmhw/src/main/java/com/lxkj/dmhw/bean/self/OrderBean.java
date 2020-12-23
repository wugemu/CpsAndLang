package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class OrderBean implements Serializable {

    private long lessSecond;
    private double goodsTotal;
    private String adr;
    private double balanceAmount;
    private String city;
    private long closeTime;
    private String country;
    private double couponAmount;
    private long couponId;
    private double couponPolicyAmount;
    private String customerRemark;
    private double giftRebateAmount;
    private String idCard;
    private int hideCount;
    private boolean ifHideBuy;
    private boolean ifShareTrade;
    private String parTradeNo;
    private String payAccount;
    private String payWay;
    private String payer;
    private long payerTime;
    private String postId;
    private double postageTotal;
    private double postalTax;
    private String province;
    private double rcvTotal;
    private long regTime;
    private String regTimeStr;
    private String rejected;
    private long sndTime;
    private String sndTo;
    private String tel;
    private String town;
    private long tradeId;
    private String tradeNo;
    private int tradeStatus;//订单状态 0：取消 1：待付款 2 待审核 5：待发货 6：待收货 7：交易完成
    private int tradeProperty;//0:大众订单 1:礼包订单 2:批零订单
    private List<OrderGoodBean> goodsList;
    private double totalAmount;//订单总价
    private boolean ifServenAfter;//是否7天之后 true是
    private boolean ifOverOder=true;//是否完结订单 true未完结  false完结
    private Object ifVipOrder;//true 玩主订单
    private double vipDiscountBaseAmount;//总优惠
    private String warehouseName;
    private String logisticsName;
    private double payAmount;//应付总额
    private int ifUserRealVip;//1-表示是的，请展示新礼包详情页面 -1 用户非vip 2 用户已经是vip但还有订单未完结
    private String problemId;//工单Id
    private String updateAdrStr;//地址修改文案
    private boolean ifUpdateTradeAdr;//true修改过地址（不展示"修改地址"按钮）
    private PhoneLTBean tradeMobileNet;//入网资料信息
    private List<TaxDetailBean> taxDetails;//税费说明明显

    private String helpTradeStr;//助力活动新增字段 助力文案
    private int helpTradeType;//助力活动订单类型 0没有 1下单返现 2免费领取
    private boolean isHelpSuccess;//助力活动新增 是否助力成功 true是
    private long helpTradeLessSecond;//助力活动新增倒计时

    private double addUserScoreNum;//订单积分
    private String cancelCopywriting;//待审核状态 取消订单文案
    private String refundCopywriting;//申请售后时提示文案
    private String canNotAfterSalesTips;//申请售后提示文案 不为空时 不可以申请售后

    private String deliveryTimeOutCompensateInfo;//超时发货补偿信息

    private boolean ifShowLogistic;//是否显示物流轨迹	false _不显示物流 true_显示物流

    private int groupStatus;//团购状态团购状态 1参团中 2团购成功 3团购失败
    private int groupType;//1普通团 2免费团
    private boolean ifCreateGroup;//是否开团者 true是
    private boolean ifGroupTrade;//是否团购订单
    private long lessGroupEndTime;//团购倒计时 剩余秒数
    private int lessGroupUserCount;//团剩余人数
    private long tradeGroupCreateId;
    private GroupOrder tradeGroupCreate;//团购订单对象
    private boolean isOnlyReturn;//是否完全返点
    private String freePop;//完全返点弹框文案
    private boolean ifBackPrice;//是否显示团长返额
    private boolean isEqualAddress;//是否集货团
    private PayResultReturnCashDetial tradeReturnCash;//下单返现详情

    public PayResultReturnCashDetial getTradeReturnCash() {
        return tradeReturnCash;
    }

    public void setTradeReturnCash(PayResultReturnCashDetial tradeReturnCash) {
        this.tradeReturnCash = tradeReturnCash;
    }

    public boolean isEqualAddress() {
        return isEqualAddress;
    }

    public void setEqualAddress(boolean equalAddress) {
        isEqualAddress = equalAddress;
    }

    public boolean isIfUpdateTradeAdr() {
        return ifUpdateTradeAdr;
    }

    public void setIfUpdateTradeAdr(boolean ifUpdateTradeAdr) {
        this.ifUpdateTradeAdr = ifUpdateTradeAdr;
    }

    public boolean isIfBackPrice() {
        return ifBackPrice;
    }

    public void setIfBackPrice(boolean ifBackPrice) {
        this.ifBackPrice = ifBackPrice;
    }

    public String getCanNotAfterSalesTips() {
        return canNotAfterSalesTips;
    }

    public void setCanNotAfterSalesTips(String canNotAfterSalesTips) {
        this.canNotAfterSalesTips = canNotAfterSalesTips;
    }

    public String getFreePop() {
        return freePop;
    }

    public void setFreePop(String freePop) {
        this.freePop = freePop;
    }

    public boolean isOnlyReturn() {
        return isOnlyReturn;
    }

    public void setOnlyReturn(boolean onlyReturn) {
        isOnlyReturn = onlyReturn;
    }

    public boolean isIfCreateGroup() {
        return ifCreateGroup;
    }

    public void setIfCreateGroup(boolean ifCreateGroup) {
        this.ifCreateGroup = ifCreateGroup;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public GroupOrder getTradeGroupCreate() {
        return tradeGroupCreate;
    }

    public void setTradeGroupCreate(GroupOrder tradeGroupCreate) {
        this.tradeGroupCreate = tradeGroupCreate;
    }

    public int getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(int groupStatus) {
        this.groupStatus = groupStatus;
    }

    public boolean isIfGroupTrade() {
        return ifGroupTrade;
    }

    public void setIfGroupTrade(boolean ifGroupTrade) {
        this.ifGroupTrade = ifGroupTrade;
    }

    public long getLessGroupEndTime() {
        return lessGroupEndTime;
    }

    public void setLessGroupEndTime(long lessGroupEndTime) {
        this.lessGroupEndTime = lessGroupEndTime;
    }

    public int getLessGroupUserCount() {
        return lessGroupUserCount;
    }

    public void setLessGroupUserCount(int lessGroupUserCount) {
        this.lessGroupUserCount = lessGroupUserCount;
    }

    public long getTradeGroupCreateId() {
        return tradeGroupCreateId;
    }

    public void setTradeGroupCreateId(long tradeGroupCreateId) {
        this.tradeGroupCreateId = tradeGroupCreateId;
    }

    public String getDeliveryTimeOutCompensateInfo() {
        return deliveryTimeOutCompensateInfo;
    }

    public void setDeliveryTimeOutCompensateInfo(String deliveryTimeOutCompensateInfo) {
        this.deliveryTimeOutCompensateInfo = deliveryTimeOutCompensateInfo;
    }

    public boolean isIfShowLogistic() {
        return ifShowLogistic;
    }

    public void setIfShowLogistic(boolean ifShowLogistic) {
        this.ifShowLogistic = ifShowLogistic;
    }


    public String getRefundCopywriting() {
        return refundCopywriting;
    }

    public void setRefundCopywriting(String refundCopywriting) {
        this.refundCopywriting = refundCopywriting;
    }

    public String getCancelCopywriting() {
        return cancelCopywriting;
    }

    public void setCancelCopywriting(String cancelCopywriting) {
        this.cancelCopywriting = cancelCopywriting;
    }

    public double getAddUserScoreNum() {
        return addUserScoreNum;
    }

    public void setAddUserScoreNum(double addUserScoreNum) {
        this.addUserScoreNum = addUserScoreNum;
    }

    public long getHelpTradeLessSecond() {
        return helpTradeLessSecond;
    }

    public void setHelpTradeLessSecond(long helpTradeLessSecond) {
        this.helpTradeLessSecond = helpTradeLessSecond;
    }

    public String getHelpTradeStr() {
        return helpTradeStr;
    }

    public void setHelpTradeStr(String helpTradeStr) {
        this.helpTradeStr = helpTradeStr;
    }

    public int getHelpTradeType() {
        return helpTradeType;
    }

    public void setHelpTradeType(int helpTradeType) {
        this.helpTradeType = helpTradeType;
    }

    public boolean isHelpSuccess() {
        return isHelpSuccess;
    }

    public void setHelpSuccess(boolean helpSuccess) {
        isHelpSuccess = helpSuccess;
    }

    public List<TaxDetailBean> getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(List<TaxDetailBean> taxDetails) {
        this.taxDetails = taxDetails;
    }

    public PhoneLTBean getTradeMobileNet() {
        return tradeMobileNet;
    }

    public void setTradeMobileNet(PhoneLTBean tradeMobileNet) {
        this.tradeMobileNet = tradeMobileNet;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getUpdateAdrStr() {
        return updateAdrStr;
    }

    public void setUpdateAdrStr(String updateAdrStr) {
        this.updateAdrStr = updateAdrStr;
    }

    public int getIfUserRealVip() {
        return ifUserRealVip;
    }

    public void setIfUserRealVip(int ifUserRealVip) {
        this.ifUserRealVip = ifUserRealVip;
    }

    public boolean isIfOverOder() {
        return ifOverOder;
    }

    public void setIfOverOder(boolean ifOverOder) {
        this.ifOverOder = ifOverOder;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getVipDiscountBaseAmount() {
        return vipDiscountBaseAmount;
    }

    public void setVipDiscountBaseAmount(double vipDiscountBaseAmount) {
        this.vipDiscountBaseAmount = vipDiscountBaseAmount;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public Object getIfVipOrder() {
        return ifVipOrder;
    }

    public void setIfVipOrder(Object ifVipOrder) {
        this.ifVipOrder = ifVipOrder;
    }

    public boolean isIfServenAfter() {
        return ifServenAfter;
    }

    public void setIfServenAfter(boolean ifServenAfter) {
        this.ifServenAfter = ifServenAfter;
    }

    public int getTradeProperty() {
        return tradeProperty;
    }

    public void setTradeProperty(int tradeProperty) {
        this.tradeProperty = tradeProperty;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getLessSecond() {
        return lessSecond;
    }

    public void setLessSecond(long lessSecond) {
        this.lessSecond = lessSecond;
    }

    public double getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(double goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public double getCouponPolicyAmount() {
        return couponPolicyAmount;
    }

    public void setCouponPolicyAmount(double couponPolicyAmount) {
        this.couponPolicyAmount = couponPolicyAmount;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public double getGiftRebateAmount() {
        return giftRebateAmount;
    }

    public void setGiftRebateAmount(double giftRebateAmount) {
        this.giftRebateAmount = giftRebateAmount;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getHideCount() {
        return hideCount;
    }

    public void setHideCount(int hideCount) {
        this.hideCount = hideCount;
    }

    public boolean isIfHideBuy() {
        return ifHideBuy;
    }

    public void setIfHideBuy(boolean ifHideBuy) {
        this.ifHideBuy = ifHideBuy;
    }

    public boolean isIfShareTrade() {
        return ifShareTrade;
    }

    public void setIfShareTrade(boolean ifShareTrade) {
        this.ifShareTrade = ifShareTrade;
    }

    public String getParTradeNo() {
        return parTradeNo;
    }

    public void setParTradeNo(String parTradeNo) {
        this.parTradeNo = parTradeNo;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public long getPayerTime() {
        return payerTime;
    }

    public void setPayerTime(long payerTime) {
        this.payerTime = payerTime;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public double getPostageTotal() {
        return postageTotal;
    }

    public void setPostageTotal(double postageTotal) {
        this.postageTotal = postageTotal;
    }

    public double getPostalTax() {
        return postalTax;
    }

    public void setPostalTax(double postalTax) {
        this.postalTax = postalTax;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getRcvTotal() {
        return rcvTotal;
    }

    public void setRcvTotal(double rcvTotal) {
        this.rcvTotal = rcvTotal;
    }

    public long getRegTime() {
        return regTime;
    }

    public void setRegTime(long regTime) {
        this.regTime = regTime;
    }

    public String getRegTimeStr() {
        return regTimeStr;
    }

    public void setRegTimeStr(String regTimeStr) {
        this.regTimeStr = regTimeStr;
    }

    public String getRejected() {
        return rejected;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }

    public long getSndTime() {
        return sndTime;
    }

    public void setSndTime(long sndTime) {
        this.sndTime = sndTime;
    }

    public String getSndTo() {
        return sndTo;
    }

    public void setSndTo(String sndTo) {
        this.sndTo = sndTo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public List<OrderGoodBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderGoodBean> goodsList) {
        this.goodsList = goodsList;
    }
}
