package com.lxkj.dmhw.presenter;

import android.util.Log;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.example.test.andlang.andlangutil.LangImageUpInterface;
import com.example.test.andlang.http.ExecutorServiceUtil;
import com.example.test.andlang.util.BitmapUtil;
import com.example.test.andlang.util.StorageUtils;
import com.example.test.andlang.util.ToastUtil;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.self.BreakUpBean;
import com.lxkj.dmhw.bean.self.CardTypeBean;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.bean.self.TradeCountBean;
import com.lxkj.dmhw.bean.self.UserAccount;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.MineModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.UMShareUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinePresenter extends BaseLangPresenter<MineModel> {


    public MinePresenter(BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(activity, modelClass);
    }

    public MinePresenter(BaseLangFragment fragment, BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(fragment, activity, modelClass);
    }

    @Override
    public void initModel() {

    }

    public void getOrderState(){

    }

    public void getUserAccountInfo(){
        //网络请求
        Map<String, Object> param = new HashMap<String, Object>();
//        BBCHttpUtil.getHttp(activity, Constants.GET_USER_INFO, param, UserAccount.class, new LangHttpInterface<UserAccount>() {
//            @Override
//            public void success(UserAccount response) {
//                model.setUserAccount(response);
//                if(response!=null){
//                    SuDianApp.getInstance().getSpUtil().putString(activity, Constants.PHONE_NUMBER, response.getMobile());
//                    SuDianApp.getInstance().getSpUtil().putInt(activity, Constants.IFBILLVIP, response.getIfBillVip());
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

    //获取各订单状态数量
    public void reqTradeCount(){
        //网络请求
        Map<String, Object> param = new HashMap<String, Object>();
        BBCHttpUtil.getHttp(activity, Constants.GET_ORDER_LIST_NUMBER, param, TradeCountBean.class, new LangHttpInterface<TradeCountBean>() {
            @Override
            public void success(TradeCountBean response) {
                model.setTradeCountBean(response);
                model.notifyData("reqTradeCount");
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

    //实名认证
    public void reqAddAuth(String realName,String cardNo,String credentialsUrl,boolean ifFlag,String cardType,boolean ifFirst){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("realName",realName);
        param.put("cardNo",cardNo);
        param.put("credentialsUrl",credentialsUrl);
        param.put("ifFlag",ifFlag);
        param.put("cardType",cardType);
        param.put("ifFirst",ifFirst);
        BBCHttpUtil.postHttp(activity, Constants.INSERT_USERIDCARD, param, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String response) {
                if(!BBCUtil.isEmpty(response)){
                    model.setAuthId(response);
                }
                model.notifyData("reqAddAuth");
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
    //修改实名认证信息
    public void reqEditAuth(String credentialsUrl,String id,int type){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id",id);
        param.put("type",type);
        if(!BBCUtil.isEmpty(credentialsUrl)) {
            param.put("credentialsUrl", credentialsUrl);
        }
        BBCHttpUtil.postHttp(activity, Constants.UPDATE_UserIdcard, param, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String response) {
                model.notifyData("reqEditAuth");
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
    //实名认证列表
    public void reqAuthList(boolean selectAuth){
        Map<String, Object> param = new HashMap<String, Object>();
        //选择购物实名认证信息时 不传此字段
        if(!selectAuth) {
            param.put("ifRealNameCard", "2");//1-是账户实名认证 2-不是不返回账户实名认证的
        }
        Type type= new TypeToken<List<UserAccount>>(){}.getType();
        BBCHttpUtil.postHttp(activity, Constants.REQ_USERIDCARD_LISt, param, type, new LangHttpInterface<List<UserAccount>>() {
            @Override
            public void success(List<UserAccount> response) {
                model.setAuthList(response);
                model.notifyData("reqAuthList");
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

    //意见反馈
    public void reqFeedBack(String description,String mobile,String attrUrl,String feedbackClass){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("description",description);
        param.put("mobile",mobile);
        param.put("attrUrl",attrUrl);
        param.put("feedbackClass",feedbackClass);
        param.put("type",1);
//        BBCHttpUtil.postHttp(activity, Constants.REQ_FEEDBACK, param, String.class, new LangHttpInterface<String>() {
//            @Override
//            public void success(String response) {
//                model.notifyData("reqFeedBack");
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

    //图片上传
    public void uploadImage(BaseLangActivity context, File file, String serverUrl, final boolean isUpHeadImg, String type){
        BBCHttpUtil.upImage(context, file, serverUrl, isUpHeadImg, type, true,new LangImageUpInterface() {
            @Override
            public void success(String resp) {
                model.setUpImgUrl(resp);
                model.notifyData("uploadImage");
            }
        });
    }

    //字典表数据获取
    public void reqParCode(int reasonType){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("type",reasonType);
        Type type=new TypeToken<List<CardTypeBean>>(){}.getType();
//        BBCHttpUtil.getHttp(activity, Constants.REQ_ParCode, params, type, new LangHttpInterface<List<CardTypeBean>>() {
//            @Override
//            public void success(List<CardTypeBean> response) {
//                model.setCardTypeBeanList(response);
//                model.notifyData("reqParCode");
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

    //获取账户信息详情
    public void reqAuthDetail() {
        Map<String, Object> param = new HashMap<String, Object>();
//        BBCHttpUtil.postHttp(activity, Constants.REQ_USERAUTH_DETIAL, param, UserAccount.class, new LangHttpInterface<UserAccount>() {
//            @Override
//            public void success(UserAccount response) {
//                model.setUserAccount(response);
//                model.notifyData("reqAuthDetail");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//                model.notifyData("reqAuthDetailError");
//            }
//
//            @Override
//            public void fail() {
//            }
//        });
    }

    /**
     * 提交其他问题
     */
//    public void submitProblem(String des, List<ADBean> imgList){
//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("description",des);
//        param.put("imgList", JsonParseUtil.gson3.toJson(imgList));
//        BBCHttpUtil.postHttp(activity, Constants.SUBMIT_PROBLEM, param, String.class, new LangHttpInterface() {
//            @Override
//            public void success(Object map) {
//                model.setOtherServiceId(map.toString());
//                model.notifyData("submitProblem");
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
//
//    }

    //分享信息接口
    public void reqShareInfo(String dataId, final String type){
        //设置分享数据
        UMShareUtil.setShareParams(dataId,type);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataId",dataId);
        params.put("type",type);//1-主题场分享 2-优惠券 3-活动商品列表 4-商品详情 5-品牌主页分享 15-订单详情页 16-下单成功页 17-邀请好友注册
//        BBCHttpUtil.getHttp(activity, Constants.REQ_ShareInfo, params, ShareBean.class, new LangHttpInterface<ShareBean>() {
//            @Override
//            public void success(ShareBean response) {
//                model.setShareBean(response);
//                if ("4".equals(type)){
//                    model.notifyData("reqShareGoodInfo");
//                }else if ("28".equals(type)){
//                    model.notifyData("reqReturnShareInfo");
//                }else {
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


    //查询是否拆单
    public void reqTradeBreakUp(String tradeNo){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tradeNo",tradeNo);
        BBCHttpUtil.postHttp(activity, Constants.REQ_BREAKUP, params, BreakUpBean.class, new LangHttpInterface<BreakUpBean>() {
            @Override
            public void success(BreakUpBean response) {
                model.setBreakUpBean(response);
                model.notifyData("reqTradeBreakUp");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {
                model.notifyData("reqTradeBreakUpError");
            }

            @Override
            public void fail() {

            }
        });
    }

    //实名认证接口添加绑定微信提现接口
    public void insertUserIdcardAndWxDraw(Map<String, Object> params){
//        params.remove("signData");
//        BBCHttpUtil.postDesHttp(activity, Constants.insertUserIdcardAndWxDraw, params, Info.class,"insertUserIdcardAndWxDraw", new LangHttpInterface<Info>() {
//            @Override
//            public void success(Info response) {
//                model.setInfo(response);
//                model.notifyData("insertUserIdcardAndWxDraw");
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

    /**
     * 支付完成获取返现数据
     * @param tradeNo
     */
    public void getAndCreateTradeReturnCash(String tradeNo) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeNo", tradeNo);
//        BBCHttpUtil.postHttp(activity, Constants.GetAndCreateTradeReturnCash, param, PayResultReturnCash.class, new LangHttpInterface<PayResultReturnCash>() {
//            @Override
//            public void success(PayResultReturnCash map) {
//                model.setPayResultReturnCash(map);
//                model.notifyData("getAndCreateTradeReturnCash");
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



    /**
     * 获取返现详情数据
     */
    public void reqTradeShareReturnCashDetial(String tradeNo) {
//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("tradeNo", tradeNo);
//        BBCHttpUtil.postHttp(activity, Constants.REQ_TRADE_SHARE_RETURN_CASH_DETIAL, param,PayResultReturnCash.class, new LangHttpInterface<PayResultReturnCash>() {
//            @Override
//            public void success(PayResultReturnCash map) {
//                model.setPayResultReturnCash(map);
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
