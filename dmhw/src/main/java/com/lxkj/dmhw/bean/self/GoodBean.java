package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GoodBean implements Serializable {

    private int activityState;//-1商品已下架 0正常 无活动 1秒杀活动中  商品详情页商品状态判断该字段
    private int status;//1上架  8下架 普通商品列表商品状态判断该字段
    private int brandGoodsNum;
    private long brandId;
    private String brandLogo;
    private String brandName;
    private String description;
    private String goodsNo;
    private boolean ifCanUseCoupon;
    private boolean ifPostFree;
    private boolean ifRealPrimise;
    private double maxIncome;
    private double profitAmount;//商品详情页赚的金额
    private double maxPrice;
    private double minIncome;
    private double minPrice;
    private double score;//礼包积分 总积分字段
    private int taxs;
    private BigDecimal appPrice;
    private String goodsId;
    private String goodsName;
    private boolean ifSetNotice;
    private String imgUrl;
    private BigDecimal marketPrice;
    private String bigImgUrl;
    private BigDecimal profit;
    private BigDecimal secProfit;//商品赚取的价格(1.0.7版本后用这个字段)
    private BigDecimal salePrice;
    private BigDecimal specialAmount;//直降多少元
    private BigDecimal specialPrice;//特价
    private List<Info> info;

    private int deliveryType;//发货方式 1-保税区 2-香港直邮 4-海外直邮 5-国内发货
    private boolean ifCompensate=true;
    private boolean ifDead;
    private boolean ifHot;
    private boolean ifNew;
    private boolean ifPackageMail;
    private boolean ifTaxFree;//是否包税
    private String specialTag;//特价标签内容 不为空则显示标签
    private boolean ifSpecOfApp;
    private boolean ifFullReduc;//是否满减 判断两个 只要有个为true
    private boolean ifActivity;//是否满减
    private boolean ifReturnWithoutReason;
    private int remainStock;// 秒杀页面
    private boolean isSellOut;//true售罄
    private boolean ifStockLow;//true库存紧张
    private int saleNum;
    private String totalSales;//销量
    private double returnAmount;
    private String returnDesc;
    private String returnTitle;
    private int type;//0_普通商品 1_礼包商品 2_组合商品 3_批零商品 内购瀑布流使用的tab5_底部占位空间

    private String incomeStr;//可赚的钱（字符串）

    private int buyStock;//已卖库存
    private int sumStock;//秒杀送库存
    private String activityGoodsLoveId;
    private boolean activityLoveIfSet;
    private int activityLoveNum;
    private String delayedReminder;
    private String countryIcon;//国旗icon
    private String countryBrandName;//国家品牌名称
    private String sendWarehouseName;//发货仓

    private String id;//内购商品明细id
    private boolean showDeleteBtn;//内购 商品是否显示删除
    private BigDecimal withinbuyPrice;//内购价格
    private BigDecimal withinBuyPrice;//往期内购使用的该次内购价格
    private BigDecimal withinbuyMaxRoyalty;//内购提成价格
    private BigDecimal withinbuyRoyalty;//现内购提成
    private BigDecimal withinbuyPrices;//现内购价
    private boolean ifAddWithInBuy;//是否加入内购会
    private int stockCnt;//内购商品库库存
    private boolean withinbuyIsCanJoin;//是否能参加内购会 1-能 0-不能
    private boolean ownMarket=true;//是否自己市场

    private long withinBuyId;//内购会id,注意！有内购会id才有内购价格
    private double realWithinBuyPrice;//列表内购价格
    private int withinbuyState;//内购加入状态 0_可以加入 1_正在内购中
    private boolean isInWithinbuyLib;// false_否 true_是
    private boolean isCheck;//编辑模式是否被选择
    private int inbuyHisCheckState;//历史内购场编辑 默认选中 0未选中 1默认选中 2失效下架或售罄

    private int loveBuyState;//想买状态 0-想买 1-已通知内购发起人
    private int loveNum;//内购想买数量
    private long timeDiff;//内购距离开始时间秒数
    private long endTimeDiff;//内购距离结束时间秒数
    private String shelf;//保质期
    private String goodsUsage;//使用方法
    private int ifSpecial;//1特价中 2即将特卖
    private int ifLivePrice;//1直播中 2即将直播

    private BigDecimal royalty;


    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public int getInbuyHisCheckState() {
        return inbuyHisCheckState;
    }

    public void setInbuyHisCheckState(int inbuyHisCheckState) {
        this.inbuyHisCheckState = inbuyHisCheckState;
    }

    public BigDecimal getRoyalty() {
        return royalty;
    }

    public void setRoyalty(BigDecimal royalty) {
        this.royalty = royalty;
    }

    public double getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(double profitAmount) {
        this.profitAmount = profitAmount;
    }

    public BigDecimal getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(BigDecimal specialPrice) {
        this.specialPrice = specialPrice;
    }

    public int getIfLivePrice() {
        return ifLivePrice;
    }

    public void setIfLivePrice(int ifLivePrice) {
        this.ifLivePrice = ifLivePrice;
    }

    public int getIfSpecial() {
        return ifSpecial;
    }

    public void setIfSpecial(int ifSpecial) {
        this.ifSpecial = ifSpecial;
    }

    public boolean isIfStockLow() {
        return ifStockLow;
    }

    public void setIfStockLow(boolean ifStockLow) {
        this.ifStockLow = ifStockLow;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getGoodsUsage() {
        return goodsUsage;
    }

    public void setGoodsUsage(String goodsUsage) {
        this.goodsUsage = goodsUsage;
    }

    public long getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(long timeDiff) {
        this.timeDiff = timeDiff;
    }

    public long getEndTimeDiff() {
        return endTimeDiff;
    }

    public void setEndTimeDiff(long endTimeDiff) {
        this.endTimeDiff = endTimeDiff;
    }

    public int getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(int loveNum) {
        this.loveNum = loveNum;
    }

    public int getLoveBuyState() {
        return loveBuyState;
    }

    public void setLoveBuyState(int loveBuyState) {
        this.loveBuyState = loveBuyState;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isInWithinbuyLib() {
        return isInWithinbuyLib;
    }

    public void setInWithinbuyLib(boolean inWithinbuyLib) {
        isInWithinbuyLib = inWithinbuyLib;
    }

    public boolean isOwnMarket() {
        return ownMarket;
    }

    public void setOwnMarket(boolean ownMarket) {
        this.ownMarket = ownMarket;
    }

    public boolean isWithinbuyIsCanJoin() {
        return withinbuyIsCanJoin;
    }

    public void setWithinbuyIsCanJoin(boolean withinbuyIsCanJoin) {
        this.withinbuyIsCanJoin = withinbuyIsCanJoin;
    }

    public int getWithinbuyState() {
        return withinbuyState;
    }

    public void setWithinbuyState(int withinbuyState) {
        this.withinbuyState = withinbuyState;
    }

    public double getRealWithinBuyPrice() {
        return realWithinBuyPrice;
    }

    public void setRealWithinBuyPrice(double realWithinBuyPrice) {
        this.realWithinBuyPrice = realWithinBuyPrice;
    }

    public long getWithinBuyId() {
        return withinBuyId;
    }

    public void setWithinBuyId(long withinBuyId) {
        this.withinBuyId = withinBuyId;
    }

    public BigDecimal getWithinBuyPrice() {
        return withinBuyPrice;
    }

    public void setWithinBuyPrice(BigDecimal withinBuyPrice) {
        this.withinBuyPrice = withinBuyPrice;
    }

    public BigDecimal getWithinbuyPrices() {
        return withinbuyPrices;
    }

    public void setWithinbuyPrices(BigDecimal withinbuyPrices) {
        this.withinbuyPrices = withinbuyPrices;
    }

    public BigDecimal getWithinbuyRoyalty() {
        return withinbuyRoyalty;
    }

    public void setWithinbuyRoyalty(BigDecimal withinbuyRoyalty) {
        this.withinbuyRoyalty = withinbuyRoyalty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIfAddWithInBuy() {
        return ifAddWithInBuy;
    }

    public void setIfAddWithInBuy(boolean ifAddWithInBuy) {
        this.ifAddWithInBuy = ifAddWithInBuy;
    }

    public int getStockCnt() {
        return stockCnt;
    }

    public void setStockCnt(int stockCnt) {
        this.stockCnt = stockCnt;
    }

    public BigDecimal getWithinbuyPrice() {
        return withinbuyPrice;
    }

    public void setWithinbuyPrice(BigDecimal withinbuyPrice) {
        this.withinbuyPrice = withinbuyPrice;
    }

    public BigDecimal getWithinbuyMaxRoyalty() {
        return withinbuyMaxRoyalty;
    }

    public void setWithinbuyMaxRoyalty(BigDecimal withinbuyMaxRoyalty) {
        this.withinbuyMaxRoyalty = withinbuyMaxRoyalty;
    }

    public boolean isShowDeleteBtn() {
        return showDeleteBtn;
    }

    public void setShowDeleteBtn(boolean showDeleteBtn) {
        this.showDeleteBtn = showDeleteBtn;
    }

    public BigDecimal getSpecialAmount() {
        return specialAmount;
    }

    public void setSpecialAmount(BigDecimal specialAmount) {
        this.specialAmount = specialAmount;
    }

    public String getCountryBrandName() {
        return countryBrandName;
    }

    public void setCountryBrandName(String countryBrandName) {
        this.countryBrandName = countryBrandName;
    }

    public String getSendWarehouseName() {
        return sendWarehouseName;
    }

    public void setSendWarehouseName(String sendWarehouseName) {
        this.sendWarehouseName = sendWarehouseName;
    }

    public String getCountryIcon() {
        return countryIcon;
    }

    public void setCountryIcon(String countryIcon) {
        this.countryIcon = countryIcon;
    }

    public String getDelayedReminder() {
        return delayedReminder;
    }

    public void setDelayedReminder(String delayedReminder) {
        this.delayedReminder = delayedReminder;
    }

    public String getActivityGoodsLoveId() {
        return activityGoodsLoveId;
    }

    public void setActivityGoodsLoveId(String activityGoodsLoveId) {
        this.activityGoodsLoveId = activityGoodsLoveId;
    }

    public boolean isActivityLoveIfSet() {
        return activityLoveIfSet;
    }

    public void setActivityLoveIfSet(boolean activityLoveIfSet) {
        this.activityLoveIfSet = activityLoveIfSet;
    }

    public int getActivityLoveNum() {
        return activityLoveNum;
    }

    public void setActivityLoveNum(int activityLoveNum) {
        this.activityLoveNum = activityLoveNum;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isIfTaxFree() {
        return ifTaxFree;
    }

    public void setIfTaxFree(boolean ifTaxFree) {
        this.ifTaxFree = ifTaxFree;
    }

    public String getSpecialTag() {
        return specialTag;
    }

    public void setSpecialTag(String specialTag) {
        this.specialTag = specialTag;
    }

    public BigDecimal getSecProfit() {
        return secProfit;
    }

    public void setSecProfit(BigDecimal secProfit) {
        this.secProfit = secProfit;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public int getBuyStock() {
        return buyStock;
    }

    public void setBuyStock(int buyStock) {
        this.buyStock = buyStock;
    }

    public int getSumStock() {
        return sumStock;
    }

    public void setSumStock(int sumStock) {
        this.sumStock = sumStock;
    }

    public String getIncomeStr() {
        return incomeStr;
    }

    public void setIncomeStr(String incomeStr) {
        this.incomeStr = incomeStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

    public boolean isIfActivity() {
        return ifActivity;
    }

    public void setIfActivity(boolean ifActivity) {
        this.ifActivity = ifActivity;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public String getReturnTitle() {
        return returnTitle;
    }

    public void setReturnTitle(String returnTitle) {
        this.returnTitle = returnTitle;
    }

    public boolean isSellOut() {
        return isSellOut;
    }

    public void setSellOut(boolean sellOut) {
        isSellOut = sellOut;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public boolean isIfReturnWithoutReason() {
        return ifReturnWithoutReason;
    }

    public void setIfReturnWithoutReason(boolean ifReturnWithoutReason) {
        this.ifReturnWithoutReason = ifReturnWithoutReason;
    }

    public String getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(String totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(BigDecimal appPrice) {
        this.appPrice = appPrice;
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

    public boolean isIfSetNotice() {
        return ifSetNotice;
    }

    public void setIfSetNotice(boolean ifSetNotice) {
        this.ifSetNotice = ifSetNotice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBigImgUrl() {
        return bigImgUrl;
    }

    public void setBigImgUrl(String bigImgUrl) {
        this.bigImgUrl = bigImgUrl;
    }

    public boolean isIfCompensate() {
        return ifCompensate;
    }

    public void setIfCompensate(boolean ifCompensate) {
        this.ifCompensate = ifCompensate;
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

    public boolean isIfFullReduc() {
        return ifFullReduc;
    }

    public void setIfFullReduc(boolean ifFullReduc) {
        this.ifFullReduc = ifFullReduc;
    }

    public void setIfSpecOfApp(boolean ifSpecOfApp) {
        this.ifSpecOfApp = ifSpecOfApp;
    }

    public int getRemainStock() {
        return remainStock;
    }

    public void setRemainStock(int remainStock) {
        this.remainStock = remainStock;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public int getActivityState() {
        return activityState;
    }

    public void setActivityState(int activityState) {
        this.activityState = activityState;
    }

    public int getBrandGoodsNum() {
        return brandGoodsNum;
    }

    public void setBrandGoodsNum(int brandGoodsNum) {
        this.brandGoodsNum = brandGoodsNum;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public boolean isIfCanUseCoupon() {
        return ifCanUseCoupon;
    }

    public void setIfCanUseCoupon(boolean ifCanUseCoupon) {
        this.ifCanUseCoupon = ifCanUseCoupon;
    }

    public boolean isIfPostFree() {
        return ifPostFree;
    }

    public void setIfPostFree(boolean ifPostFree) {
        this.ifPostFree = ifPostFree;
    }

    public boolean isIfRealPrimise() {
        return ifRealPrimise;
    }

    public void setIfRealPrimise(boolean ifRealPrimise) {
        this.ifRealPrimise = ifRealPrimise;
    }

    public double getMaxIncome() {
        return maxIncome;
    }

    public void setMaxIncome(double maxIncome) {
        this.maxIncome = maxIncome;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinIncome() {
        return minIncome;
    }

    public void setMinIncome(double minIncome) {
        this.minIncome = minIncome;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public int getTaxs() {
        return taxs;
    }

    public void setTaxs(int taxs) {
        this.taxs = taxs;
    }

}
