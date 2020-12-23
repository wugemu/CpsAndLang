package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created By lhp
 * on 2019/12/9
 */
public class Group implements Serializable {


    /**
     * goodsGroupPrice : 67670
     * groupType : 83741
     * appPrice : 0
     * couponRate : 0
     * deliveryType : 1
     * goodsId : 256595020053405
     * goodsName : FEBREZE/风倍清 车内空调夹式清新剂 2ML
     * groupBuyStock : 21
     * groupCount : 0
     * groupId : 1
     * ifActivity : false
     * ifDead : false
     * ifHot : false
     * ifNew : false
     * ifPackageMail : true
     * ifSpecOfApp : false
     * imgUrl : https://image.sudian178.com/sd/middleImg/5851956109921022.jpg
     * isOnline : true
     * isSellOut : true
     * status : 1
     */

    private BigDecimal goodsGroupPrice;//团购价格
    private int groupType;//团购类型 1普通团 2团长免费领
    private boolean ifBackPrice;//团长返额标示 未确定字段
    private BigDecimal appPrice;
    private BigDecimal couponRate;
    private int deliveryType;
    private String goodsId;
    private String goodsImgUrl;//商品图片
    private long withinBuyId;
    private String goodsName;
    private int groupBuyStock;
    private int groupCount;
    private String groupId;
    private boolean ifActivity;
    private boolean ifDead;
    private boolean ifHot;
    private boolean ifNew;
    private boolean ifPackageMail;
    private boolean ifSpecOfApp;
    private String imgUrl;//商品图片
    private boolean isOnline;//是否上线
    private boolean isSellOut;//是否售罄
    private int status;//商品状态
    private String description;//描述
    private boolean ifCompensate=true;//假一赔十
    private boolean ifTaxFree;//是否包税
    private int groupLessStock;//团购剩余库存 真实总库存
    private int groupStock;//团购总库存
    private boolean isStockNervous;
    private int userGroupStock;//已使用团购库存
    private List<String> heardUrls;//成员头像list
    private String heardUrl;//头像
    private String id;//团创建的id
    private int lessGroupUserCount;//剩余拼团人数
    private List<String> nickNames;//昵称列表
    private long lessGroupEndTime;//可参团结束时间
    private boolean ifOwnGroup;//是否我自己参与的团
    private long groupStartTime;//团购开始时间
    private double groupMaxPrice;//团购最高价
    private double groupMinPrice;//团购最低价
    private double maxPrice;//团购最高价
    private double minPrice;//团购最低价
    private long groupLessTime;//详情页团购结束时间
    private String groupContent;//拼团规则文案
    private String timeDayStr;//

    public boolean isIfBackPrice() {
        return ifBackPrice;
    }

    public void setIfBackPrice(boolean ifBackPrice) {
        this.ifBackPrice = ifBackPrice;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getHeardUrl() {
        return heardUrl;
    }

    public void setHeardUrl(String heardUrl) {
        this.heardUrl = heardUrl;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public long getWithinBuyId() {
        return withinBuyId;
    }

    public void setWithinBuyId(long withinBuyId) {
        this.withinBuyId = withinBuyId;
    }

    public String getTimeDayStr() {
        return timeDayStr;
    }

    public void setTimeDayStr(String timeDayStr) {
        this.timeDayStr = timeDayStr;
    }

    public String getGroupContent() {
        return groupContent;
    }

    public void setGroupContent(String groupContent) {
        this.groupContent = groupContent;
    }

    public long getGroupLessTime() {
        return groupLessTime;
    }

    public void setGroupLessTime(long groupLessTime) {
        this.groupLessTime = groupLessTime;
    }

    public double getGroupMaxPrice() {
        return groupMaxPrice;
    }

    public void setGroupMaxPrice(double groupMaxPrice) {
        this.groupMaxPrice = groupMaxPrice;
    }

    public double getGroupMinPrice() {
        return groupMinPrice;
    }

    public void setGroupMinPrice(double groupMinPrice) {
        this.groupMinPrice = groupMinPrice;
    }

    public long getGroupStartTime() {
        return groupStartTime;
    }

    public void setGroupStartTime(long groupStartTime) {
        this.groupStartTime = groupStartTime;
    }

    public boolean isIfOwnGroup() {
        return ifOwnGroup;
    }

    public void setIfOwnGroup(boolean ifOwnGroup) {
        this.ifOwnGroup = ifOwnGroup;
    }

    public List<String> getHeardUrls() {
        return heardUrls;
    }

    public void setHeardUrls(List<String> heardUrls) {
        this.heardUrls = heardUrls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLessGroupUserCount() {
        return lessGroupUserCount;
    }

    public void setLessGroupUserCount(int lessGroupUserCount) {
        this.lessGroupUserCount = lessGroupUserCount;
    }

    public List<String> getNickNames() {
        return nickNames;
    }

    public void setNickNames(List<String> nickNames) {
        this.nickNames = nickNames;
    }

    public long getLessGroupEndTime() {
        return lessGroupEndTime;
    }

    public void setLessGroupEndTime(long lessGroupEndTime) {
        this.lessGroupEndTime = lessGroupEndTime;
    }

    public int getGroupLessStock() {
        return groupLessStock;
    }

    public void setGroupLessStock(int groupLessStock) {
        this.groupLessStock = groupLessStock;
    }

    public int getGroupStock() {
        return groupStock;
    }

    public void setGroupStock(int groupStock) {
        this.groupStock = groupStock;
    }

    public boolean isStockNervous() {
        return isStockNervous;
    }

    public void setStockNervous(boolean stockNervous) {
        isStockNervous = stockNervous;
    }

    public int getUserGroupStock() {
        return userGroupStock;
    }

    public void setUserGroupStock(int userGroupStock) {
        this.userGroupStock = userGroupStock;
    }

    public boolean isIfTaxFree() {
        return ifTaxFree;
    }

    public void setIfTaxFree(boolean ifTaxFree) {
        this.ifTaxFree = ifTaxFree;
    }

    public boolean isIfCompensate() {
        return ifCompensate;
    }

    public void setIfCompensate(boolean ifCompensate) {
        this.ifCompensate = ifCompensate;
    }

    public BigDecimal getGoodsGroupPrice() {
        return goodsGroupPrice;
    }

    public void setGoodsGroupPrice(BigDecimal goodsGroupPrice) {
        this.goodsGroupPrice = goodsGroupPrice;
    }

    public BigDecimal getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(BigDecimal appPrice) {
        this.appPrice = appPrice;
    }

    public BigDecimal getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(BigDecimal couponRate) {
        this.couponRate = couponRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }


    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGroupBuyStock() {
        return groupBuyStock;
    }

    public void setGroupBuyStock(int groupBuyStock) {
        this.groupBuyStock = groupBuyStock;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isIfActivity() {
        return ifActivity;
    }

    public void setIfActivity(boolean ifActivity) {
        this.ifActivity = ifActivity;
    }

    public boolean isIfDead() {
        return ifDead;
    }

    public void setIfDead(boolean ifDead) {
        this.ifDead = ifDead;
    }

    public boolean isIfHot() {
        return ifHot;
    }

    public void setIfHot(boolean ifHot) {
        this.ifHot = ifHot;
    }

    public boolean isIfNew() {
        return ifNew;
    }

    public void setIfNew(boolean ifNew) {
        this.ifNew = ifNew;
    }

    public boolean isIfPackageMail() {
        return ifPackageMail;
    }

    public void setIfPackageMail(boolean ifPackageMail) {
        this.ifPackageMail = ifPackageMail;
    }

    public boolean isIfSpecOfApp() {
        return ifSpecOfApp;
    }

    public void setIfSpecOfApp(boolean ifSpecOfApp) {
        this.ifSpecOfApp = ifSpecOfApp;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isSellOut() {
        return isSellOut;
    }

    public void setSellOut(boolean sellOut) {
        isSellOut = sellOut;
    }
}
