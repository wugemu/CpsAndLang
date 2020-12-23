package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

/**
 * Created By lhp
 * on 2019/12/11
 */
public class GroupOrder implements Serializable {


    /**
     * createTimeStr : 2019-12-02 14:22:51
     * goodsId : 256595020031755
     * goodsName : KRACIE/肌美精 保湿渗透面膜  收缩毛孔 黑面膜 4片
     * groupCount : 86055
     * groupFailType : 0
     * groupId : 1
     * groupStatus : 1
     * groupType : 1
     * id : 262487890000288
     * ifCreateGroup : true
     * isEqualAddress : true
     * isStockNervous : false
     * lessGroupEndTime : 0
     * lessGroupUserCount : 6
     * tradeGroupAddress : 陈* 18***117 浙江省杭州市西湖区 OK了据嗯特图也
     */

    private String createTimeStr;
    private String goodsId;
    private long withinBuyId;
    private String goodsName;
    private int groupCount;
    private int groupFailType;
    private String groupId;
    private int groupStatus;
    private int groupType;//1是普通团 2是团长免费团
    private String id;
    private boolean ifCreateGroup;
    private boolean isEqualAddress;
    private boolean isStockNervous;
    private int lessGroupEndTime;
    private int lessGroupUserCount;
    private String tradeGroupAddress;
    private List<GroupUser> groupTradeUserList;
    private String groupContent;
    private long groupSuccessTime;//团成功的时间
    private List<String> heardUrls;
    private int useGroupCount;//已拼团人数
    private String freeGroupRefundStr;//团长免费团申请售后提示文案
    private String groupSuccessTimeStr;//拼图时间
    private boolean isPay;//团支付是否成功
    private long tradeId;//订单号
    private boolean ifBackPrice;//是否显示团长返额

    public boolean isIfBackPrice() {
        return ifBackPrice;
    }

    public void setIfBackPrice(boolean ifBackPrice) {
        this.ifBackPrice = ifBackPrice;
    }

    public long getWithinBuyId() {
        return withinBuyId;
    }

    public void setWithinBuyId(long withinBuyId) {
        this.withinBuyId = withinBuyId;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public String getGroupSuccessTimeStr() {
        return groupSuccessTimeStr;
    }

    public void setGroupSuccessTimeStr(String groupSuccessTimeStr) {
        this.groupSuccessTimeStr = groupSuccessTimeStr;
    }

    public String getFreeGroupRefundStr() {
        return freeGroupRefundStr;
    }

    public void setFreeGroupRefundStr(String freeGroupRefundStr) {
        this.freeGroupRefundStr = freeGroupRefundStr;
    }

    public long getGroupSuccessTime() {
        return groupSuccessTime;
    }

    public void setGroupSuccessTime(long groupSuccessTime) {
        this.groupSuccessTime = groupSuccessTime;
    }

    public List<String> getHeardUrls() {
        return heardUrls;
    }

    public void setHeardUrls(List<String> heardUrls) {
        this.heardUrls = heardUrls;
    }

    public int getUseGroupCount() {
        return useGroupCount;
    }

    public void setUseGroupCount(int useGroupCount) {
        this.useGroupCount = useGroupCount;
    }

    public String getGroupContent() {
        return groupContent;
    }

    public void setGroupContent(String groupContent) {
        this.groupContent = groupContent;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
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

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public int getGroupFailType() {
        return groupFailType;
    }

    public void setGroupFailType(int groupFailType) {
        this.groupFailType = groupFailType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(int groupStatus) {
        this.groupStatus = groupStatus;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIfCreateGroup() {
        return ifCreateGroup;
    }

    public void setIfCreateGroup(boolean ifCreateGroup) {
        this.ifCreateGroup = ifCreateGroup;
    }

    public boolean isEqualAddress() {
        return isEqualAddress;
    }

    public void setEqualAddress(boolean equalAddress) {
        isEqualAddress = equalAddress;
    }

    public boolean isStockNervous() {
        return isStockNervous;
    }

    public void setStockNervous(boolean stockNervous) {
        isStockNervous = stockNervous;
    }

    public int getLessGroupEndTime() {
        return lessGroupEndTime;
    }

    public void setLessGroupEndTime(int lessGroupEndTime) {
        this.lessGroupEndTime = lessGroupEndTime;
    }

    public int getLessGroupUserCount() {
        return lessGroupUserCount;
    }

    public void setLessGroupUserCount(int lessGroupUserCount) {
        this.lessGroupUserCount = lessGroupUserCount;
    }

    public String getTradeGroupAddress() {
        return tradeGroupAddress;
    }

    public void setTradeGroupAddress(String tradeGroupAddress) {
        this.tradeGroupAddress = tradeGroupAddress;
    }

    public List<GroupUser> getGroupTradeUserList() {
        return groupTradeUserList;
    }

    public void setGroupTradeUserList(List<GroupUser> groupTradeUserList) {
        this.groupTradeUserList = groupTradeUserList;
    }
}
