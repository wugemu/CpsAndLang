package com.lxkj.dmhw.presenter;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.example.test.andlang.util.MMKVUtil;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.self.BasePage;
import com.lxkj.dmhw.bean.self.DeclarationDetailBean;
import com.lxkj.dmhw.bean.self.GoodBuyStateBean;
import com.lxkj.dmhw.bean.self.LogisticsBean;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.bean.self.PayResultReturnCash;
import com.lxkj.dmhw.bean.self.RefundLogistics;
import com.lxkj.dmhw.bean.self.RefundPost;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.bean.self.TradeSkuVO;
import com.lxkj.dmhw.bean.self.UserAccount;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.OrderModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;
import com.lxkj.dmhw.utils.JsonParseUtil;
import com.lxkj.dmhw.utils.UMShareUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderPresenter extends BaseLangPresenter<OrderModel> {
    private int pageIndex = 1;
    public boolean haveMore = true;

    public OrderPresenter(BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(activity, modelClass);
    }

    public OrderPresenter(BaseLangFragment fragment, BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(fragment, activity, modelClass);
    }

    @Override
    public void initModel() {

    }

    //订单列表
    public void reqOrderList(int status,int orderType,boolean loadMore){
        if (!loadMore) {
            pageIndex = 1;
            haveMore = true;
        } else {
            haveMore = false;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status",status);
        params.put("type",orderType);
        params.put("pageNum",pageIndex);
        Type type= new TypeToken<BasePage<OrderBean>>(){}.getType();
        BBCHttpUtil.postHttp(activity, Constants.GET_ORDER_LIST_BY_STATUS, params, type, new LangHttpInterface<BasePage<OrderBean>>() {
            @Override
            public void success(BasePage<OrderBean> response) {
                if(model.getOrderBeanList()==null){
                    model.setOrderBeanList(new ArrayList<OrderBean>());
                }
                if (pageIndex == 1) {
                    model.getOrderBeanList().clear();
                }
                if(response.getList()!=null) {
                    model.getOrderBeanList().addAll(response.getList());
                }
                haveMore = response.isHasNextPage();
                pageIndex++;
                model.notifyData("reqOrderList");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {
                model.notifyData("error");
            }

            @Override
            public void fail() {

            }
        });
    }

    //订单详情页
    public void reqOrderDetial(long tradeId,int orderType){
        Map<String,Object> params=new HashMap<>();
        params.put("tradeId",tradeId);
        params.put("type",orderType);
        Type type= new TypeToken<OrderBean>(){}.getType();

        BBCHttpUtil.postHttp(activity, Constants.GET_ORDER_INFO_BY_NO, params, type, new LangHttpInterface<OrderBean>() {
            @Override
            public void success(OrderBean map) {
                model.setOrderBean(map);
                model.notifyData("reqOrderDetial");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {

            }

            @Override
            public void fail() {

            }
        });
    }

    //删除订单
    public void reqDelOrder(String tradeId){
        Map<String,Object> params=new HashMap<>();
        params.put("tradeId",tradeId);
        BBCHttpUtil.postHttp(activity, Constants.DELETE_ORDER_BY_NO, params, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String map) {
                model.notifyData("reqDelOrder");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {

            }

            @Override
            public void fail() {

            }
        });
    }

    //取消订单
    public void reqCancelOrder(String tradeId){
        Map<String,Object> params=new HashMap<>();
        params.put("tradeId",tradeId);
        BBCHttpUtil.postHttp(activity, Constants.CANCLE_ORDER_BY_NO, params, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String map) {
                model.notifyData("reqCancelOrder");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {

            }

            @Override
            public void fail() {

            }
        });
    }

    //确定收货
    public void reqConfirmOrder(String tradeId){
        Map<String,Object> params=new HashMap<>();
        params.put("tradeId",tradeId);
        BBCHttpUtil.postHttp(activity, Constants.UPDATE_ORDER_INFO, params, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String map) {
                model.notifyData("reqConfirmOrder");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {

            }

            @Override
            public void fail() {

            }
        });
    }

    public void reqLogisticsDetail(String postId,long tradeId){
        Map<String,Object> params=new HashMap<>();
        params.put("postId",postId);
        if(tradeId>0){
            params.put("tradeId",tradeId);
        }
        Type type= new TypeToken<List<BasePage<LogisticsBean>>>(){}.getType();

        BBCHttpUtil.postHttp(activity, Constants.GET_LOGISTICS_INFO_BY_POST_ID, params, type, new LangHttpInterface<List<BasePage<LogisticsBean>>>() {
            @Override
            public void success(List<BasePage<LogisticsBean>> map) {
                model.setLogistMap(map);
                model.notifyData("reqLogisticsDetail");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {

            }

            @Override
            public void fail() {

            }
        });
    }
    public void reqLogisticsRefund(String refundId){
        Map<String,Object> params=new HashMap<>();
        params.put("refundId",refundId);
        Type type= new TypeToken<List<RefundLogistics>>(){}.getType();
//        BBCHttpUtil.postHttp(activity, Constants.GET_LOGISTICS_REFUND, params, type, new LangHttpInterface<List<RefundLogistics>>() {
//            @Override
//            public void success(List<RefundLogistics> map) {
//                model.setRefundLogistics(map);
//                model.notifyData("reqLogisticsRefund");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    //加入购物车
    public void reqChangeCart(List<TradeSkuVO> tradeSkuVOList){
        String tradeSkuVOStr="";
        if(tradeSkuVOList!=null){
            tradeSkuVOStr= JsonParseUtil.gson3.toJson(tradeSkuVOList);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeSkuVO",tradeSkuVOStr);
        BBCHttpUtil.postHttp(activity, Constants.BATCH_ADD_CART, param, String.class, new LangHttpInterface() {
            @Override
            public void success(Object map) {
                model.notifyData("reqChangeCart");
            }

            @Override
            public void empty() {
            }

            @Override
            public void error() {
            }

            @Override
            public void fail() {
            }
        });

    }

    //订单去支付查询接口
    public void reqOrderToPay(String tradeNo){
        Map<String,Object> params=new HashMap<>();
        params.put("tradeNo",tradeNo);
        BBCHttpUtil.postHttp(activity, Constants.REQ_ORDERTOPAY, params, OrderModel.class, new LangHttpInterface<OrderModel>() {
            @Override
            public void success(OrderModel map) {
                model.setAmount(map.getAmount());
                model.setIfCheckSms(map.getIfCheckSms());
                model.setMobile(map.getMobile());
                model.setTradeNo(map.getTradeNo());
                model.setPayCountDown(map.getPayCountDown());
                model.notifyData("reqOrderToPay");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {

            }

            @Override
            public void fail() {

            }
        });
    }

    //余额支付时候发送短信验证码
    public void sendSafeCode(String tradeNo){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("tradeNo",tradeNo);
//        BBCHttpUtil.postDesHttp(activity, Constants.SEND_SMSFORPAY, params, String.class, "sendSafeCode",new LangHttpInterface<String>() {
//            @Override
//            public void success(String response) {
//                model.notifyData("sendSafeCode");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    //短信验证码支付校验接口
    public void checkSmsForCachOrder(String captcha,String tradeNo){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("captcha",captcha);
        params.put("tradeNo",tradeNo);
//        BBCHttpUtil.postHttp(activity, Constants.POST_SAFECODE_PAY, params, String.class, new LangHttpInterface<String>() {
//            @Override
//            public void success(String response) {
//                model.notifyData("checkSmsForCachOrder");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    //
    public void checkOrderBuy(List<GoodBuyStateBean> goodBuyStateBeanList){

        model.setGoodBuyStateBeanList(goodBuyStateBeanList);
        model.notifyData("checkOrderBuy");

//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("tradeId",tradeId);
//        Type type=new TypeToken<List<GoodBuyStateBean>>(){}.getType();
//        BBCHttpUtil.postHttp(activity, Constants.REQ_checkOrderBuy, params, type, new LangHttpInterface<List<GoodBuyStateBean>>() {
//            @Override
//            public void success(List<GoodBuyStateBean> response) {
//                model.setGoodBuyStateBeanList(response);
//                model.notifyData("checkOrderBuy");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    //确认订单已完结
    public void confirmTradeClose(String tradeId){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("tradeId",tradeId);
//        BBCHttpUtil.postHttp(activity, Constants.confirmTradeClose, params, String.class, new LangHttpInterface<String>() {
//            @Override
//            public void success(String response) {
//                model.notifyData("confirmTradeClose");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    public void getUserAccountInfo(){
        //网络请求
        Map<String, Object> param = new HashMap<String, Object>();
//        BBCHttpUtil.postHttp(activity, Constants.GET_USER_INFO, param, UserAccount.class, new LangHttpInterface<UserAccount>() {
//            @Override
//            public void success(UserAccount response) {
//                if(response!=null){
//                    MMKVUtil.putInt(Constants.IFBILLVIP, response.getIfBillVip());
//                }
//                model.notifyData("getUserAccountInfo");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//            }
//        });
    }

    //分享信息接口
    public void reqShareInfo(String dataId, final String type){
        //设置分享数据
        UMShareUtil.setShareParams(dataId,type);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataId",dataId);
        params.put("type",type);//1-主题场分享 2-优惠券 3-活动商品列表 4-商品详情 5-品牌主页分享 15-订单详情页 16-下单成功页 17-邀请好友注册 20-助力订单分享 21-团分享
//        BBCHttpUtil.getHttp(activity, Constants.REQ_ShareInfo, params, ShareBean.class, new LangHttpInterface<ShareBean>() {
//            @Override
//            public void success(ShareBean response) {
//                model.setShareBean(response);
//                if ("4".equals(type)){
//                    model.notifyData("reqShareGoodInfo");
//                }else if("20".equals(type)){
//                    //20-助力订单分享
//                    model.notifyData("reqShareInfoZL");
//                }else if ("21".equals(type)){
//                    model.notifyData("reqShareGroupInfo");
//                }else if ("28".equals(type)){
//                    model.notifyData("reqReturnShareInfo");
//                }else{
//                    model.notifyData("reqShareInfo");
//                }
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//                model.notifyData("error");
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    //分类品牌搜索
    public void reqRefundPostList(){
//        Map<String, Object> params = new HashMap<String, Object>();
//        if(!BBCUtil.isEmpty(id)) {
//            params.put("categoryId", id);
//        }
        Type type=new TypeToken<LinkedHashMap<String,List<RefundPost>>>(){}.getType();
//        BBCHttpUtil.getHttp(activity, Constants.REQ_REFUND_POST_LIST, null,type, new LangHttpInterface<LinkedHashMap<String,List<RefundPost>>>() {
//            @Override
//            public void success(LinkedHashMap<String,List<RefundPost>> response) {
//                model.setPostMap(response);
//                model.notifyData("reqRefundPostList");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    //获取订单报关信息
    public void tradeDeclarationDetail(String tradeId){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("tradeId",tradeId);
//        BBCHttpUtil.postHttp(activity, Constants.REQ_Declaration_INFO, params, DeclarationDetailBean.class, new LangHttpInterface<DeclarationDetailBean>() {
//            @Override
//            public void success(DeclarationDetailBean response) {
//                model.setDeclarationDetailBean(response);
//                model.notifyData("tradeDeclarationDetail");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    public void updateTradeDeclaration(String tradeNo,String idCard,String realName){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("tradeNo",tradeNo);
        params.put("idCard",idCard);
        params.put("realName",realName);
//        BBCHttpUtil.postHttp(activity, Constants.REQ_UPDATE_DECLARATION, params, String.class, new LangHttpInterface<String>() {
//            @Override
//            public void success(String response) {
//                model.notifyData("updateTradeDeclaration");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    //订单搜索
    public void searchOrder(int mType,String keyWord,boolean loadMore){
        if (!loadMore) {
            pageIndex = 1;
            haveMore = true;
        } else {
            haveMore = false;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyWord",keyWord);
        params.put("type",mType);
        params.put("page",pageIndex);
        Type type= new TypeToken<BasePage<OrderBean>>(){}.getType();
//        BBCHttpUtil.postHttp(activity, Constants.SEARCH_ORDER, params, type, new LangHttpInterface<BasePage<OrderBean>>() {
//            @Override
//            public void success(BasePage<OrderBean> response) {
//                if(model.getOrderBeanList()==null){
//                    model.setOrderBeanList(new ArrayList<OrderBean>());
//                }
//                if (pageIndex == 1) {
//                    model.getOrderBeanList().clear();
//                }
//                if(response.getList()!=null) {
//                    model.getOrderBeanList().addAll(response.getList());
//                }
//                haveMore = response.isHasNextPage();
//                pageIndex++;
//                model.notifyData("searchOrder");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//                model.notifyData("error");
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    /**
     * 获取返现详情数据
     */
    public void reqTradeShareReturnCashDetial(String tradeNo) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeNo", tradeNo);
//        BBCHttpUtil.postHttp(activity, Constants.REQ_TRADE_SHARE_RETURN_CASH_DETIAL, param,PayResultReturnCash.class, new LangHttpInterface<PayResultReturnCash>() {
//            @Override
//            public void success(PayResultReturnCash map) {
//                model.notifyData("reqTradeShareReturnCashDetial");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }
}
