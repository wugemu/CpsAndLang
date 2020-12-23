package com.lxkj.dmhw.logic;

public class Constants {
    public static final int appType=1; //0-cps 1-自营
    //微信id
    public static final String WXAPPID="";
    public static String imageDir = "nncps";

    public static final double COUPON_ORDERGIFT_SIZE = 138.0 / 710.0;//订单分享优惠券显示尺寸
    public static final String TIME_TASK = "SD_TIME_TASK";
    public static final String IFBILLVIP = "ifBillVip";//-1 未登录 会员等级 1玩客 2玩主 3金牌玩主 4 钻石玩主

    public static final String HOST = "http://cps.17gouba.com";// 正式环境地址

    public static final String HOME_INDEX_ONE="/yxrweb/core/indexInfoOne";//首页第一页
    public static final String HOME_INDEX_TWO="/yxrweb/goods/material/findIndexMaterialDetail";//好物种草
    public static final String HOME_INDEX_THREE="/yxrweb/goods/activity/querySecActivityNow";//首页默认秒杀场次查询
    public static final String REQ_HOT_GOODS="/yxrweb/core/queryHotIndexGoods";//首页好物推荐
    public static final String REQ_ACTIVITYGOODS="/yxrweb/goods/activity/querySecActivityGoods";//秒杀商品查询
    public static final String GET_PRODUCT_INFO="/yxrweb/goods/queryGodosDetail";//商品详情
    public static final String BATCH_ADD_CART="/yxrweb/car/addShopCar";//加入购物车
    public static final String GET_CART_COUNT="/yxrweb/car/carCount";//购物车数量获取
    public static final String GET_CART_LIST_NEW="/yxrweb/car/getCarIndex";
    public static final String DELETE_CART_BY_ID="/yxrweb/car/delShopCar";//删除购物车
    public static final String CLEAR_INVALID_CART_LIST="/yxrweb/car/delInvalidShopCar";//清空失效商品
    public static final String GET_COUPON_LIST="/yxrweb/coupon/getMyConpou";
    public static final String EXCHANGE_COUPON="/yxrweb/coupon/takeConpouByCode";
    public static final String GET_INFO_BY_SETTLEMENT="/yxrweb/trade/sure/queryBaseData";
    public static final String getCouponsForBuy="/yxrweb/coupon/queryTradeCanUserCoupons";
    public static final String getAmount="/yxrweb/trade/sure/getAmount";
    public static final String CREATE_ORDER="/yxrweb/trade/sure/createTrade";//创建订单
    public static final String POST_PAY="/yxrweb/payment/getPrePayDetail";//获取支付凭证
    public static final String REQ_ORDERTOPAY="/yxrweb/trade/sure/queryToPay";//订单确认订单信息查询
    public static final String GET_INVALID_CART_LIST="/yxrweb/car/getShopCarIndexTwo";//失效商品
    public static final String REQ_ADDRLIST="/yxrweb/userAddress/getAddressList";//地址列表
    public static final String REQ_ONLINEADDR="/yxrweb/base/queryAddress";//地址省市区
    public static final String ADD_ADDRESS="/yxrweb/userAddress/insertAddr";//添加地址
    public static final String EDIT_ADDRESS="/yxrweb/userAddress/updateAddr";//修改地址
    public static final String DELETE_ADDRESS="/yxrweb/userAddress/delAddress";//删除地址
    public static final String INCOME_DATA="/yxrweb/user/detail/getUserEaringPreListGroup";//预收益总和获取
    public static final String INCOME_DATA_DETIAL_LIST="/yxrweb/user/detail/getUserEaringPreList";//收益明细列表

    public static final String GET_ORDER_LIST_NUMBER="/yxrweb/trade/findTradeCount";//订单数量
    public static final String INSERT_USERIDCARD="/yxrweb/idcard/insertUserIdcard";//添加用户实名认证
    public static final String UPDATE_UserIdcard="/yxrweb/idcard/updateUserIdcard";//修改实名认证
    public static final String UPDATE_TRADEADDRESS="/yxrweb/problem/updateTradeAddress";//修改订单地址
    public static final String REQ_USERIDCARD_LISt="/yxrweb/idcard/getUserIdcard";
    public static final String REQ_BREAKUP="/yxrweb/trade/getTradeIsBreakUp";//查询订单是否拆单
    public static final String UP_MINE_HEADIMG="/yxrweb/upload/uploadFile";
    public static final String GET_ORDER_LIST_BY_STATUS="/yxrweb/trade/queryPageTrade";//订单列表
    public static final String GET_ORDER_INFO_BY_NO="/yxrweb/trade/queryDetailByTradeId";//订单详情
    public static final String DELETE_ORDER_BY_NO="/yxrweb/trade/delTrade";//删除订单
    public static final String CANCLE_ORDER_BY_NO="/yxrweb/trade/cancleTrade";//取消订单
    public static final String UPDATE_ORDER_INFO="/yxrweb/trade/sure/queryBaseData";//确认订单
    public static final String GET_LOGISTICS_INFO_BY_POST_ID="/yxrweb/trade/queryLogist";//物流详情
    public static final String REQ_quertMaterialBase="/yxrweb/goods/material/quertMaterialBase";//种草分类
    public static final String REQ_findMaterialDetailPage="/yxrweb/goods/material/findMaterialDetailPage";//种草列表




}
