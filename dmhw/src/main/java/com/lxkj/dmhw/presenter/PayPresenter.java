package com.lxkj.dmhw.presenter;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.example.test.andlang.andlangutil.LangImageUpInterface;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.self.AddrBean;
import com.lxkj.dmhw.bean.self.AmountSettleBean;
import com.lxkj.dmhw.bean.self.BasePage;
import com.lxkj.dmhw.bean.self.ChangeTradeBean;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.bean.self.CouponSettleBean;
import com.lxkj.dmhw.bean.self.CreateOrderBean;
import com.lxkj.dmhw.bean.self.PhoneLTBean;
import com.lxkj.dmhw.bean.self.PhoneLTDetailBean;
import com.lxkj.dmhw.bean.self.TopNotifyBean;
import com.lxkj.dmhw.bean.self.TradeSkuVO;
import com.lxkj.dmhw.bean.self.WxPrePayBean;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.PayModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.JsonParseUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayPresenter extends BaseLangPresenter<PayModel> {
    private int pageIndex = 1;
    public boolean haveMore = true;

    public PayPresenter(BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(activity, modelClass);
    }

    @Override
    public void initModel() {

    }
    //查询确认订单信息
    public void reqSettlementInfo(List<TradeSkuVO> tradeSkuVOList, String addrId, String idCard, String groupId, String tradeGroupCreateId){
        String tradeSkuVOStr="";
        if(tradeSkuVOList!=null){
            tradeSkuVOStr= JsonParseUtil.gson3.toJson(tradeSkuVOList);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("createVo",tradeSkuVOStr);
        if(!BBCUtil.isEmpty(addrId)){
            param.put("addrId",addrId);
        }
        if(!BBCUtil.isEmpty(groupId)){
            param.put("groupId",groupId);
        }
        if(!BBCUtil.isEmpty(tradeGroupCreateId)){
            param.put("tradeGroupCreateId",tradeGroupCreateId);
        }
        if(!BBCUtil.isEmpty(idCard)){
            param.put("idCardId",idCard);
        }
        BBCHttpUtil.postHttp(activity, Constants.GET_INFO_BY_SETTLEMENT, param, PayModel.class, new LangHttpInterface<PayModel>() {
            @Override
            public void success(PayModel response) {
                model.setIfNeedCardId(response.isIfNeedCardId());
                model.setIfLiantongCard(response.isIfLiantongCard());
                model.setSkuList(response.getSkuList());
                model.setUserAddr(response.getUserAddr());
                model.setIfNeedShowServiceAgreement(response.isIfNeedShowServiceAgreement());
                model.setUserIdcard(response.getUserIdcard());
                model.setOrderDelayGoodsReminderList(response.getOrderDelayGoodsReminderList());
                model.setGroupUserInfo(response.getGroupUserInfo());
                model.setEqualAddress(response.isEqualAddress());
                model.setAddressContent(response.getAddressContent());
                model.setEqualAddressShow(response.isEqualAddressShow());
                model.setAddressPop(response.getAddressPop());
                model.setFreePop(response.getFreePop());
                model.setFreeReturn(response.isFreeReturn());
                model.setIfReturnCash(response.isIfReturnCash());
                model.setReturnCashTitle(response.getReturnCashTitle());
                model.setReturnAbleCashAmount(response.getReturnAbleCashAmount());
                model.notifyData("reqSettlementInfo");
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


    //查询可用的优惠券数量
    public void countCouponsForBuy(List<TradeSkuVO> tradeSkuVOList){
        String tradeSkuVOStr="";
        if(tradeSkuVOList!=null){
            tradeSkuVOStr= JsonParseUtil.gson3.toJson(tradeSkuVOList);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeSkuVO",tradeSkuVOStr);
//        BBCHttpUtil.postHttp(activity, Constants.countCouponsForBuy, param, String.class, new LangHttpInterface<String>() {
//            @Override
//            public void success(String response) {
//                model.setCouponNum(response);
//                model.notifyData("countCouponsForBuy");
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

    //查询可用的优惠券
    public void getCouponsForBuy(List<TradeSkuVO> tradeSkuVOList){
        String tradeSkuVOStr="";
        if(tradeSkuVOList!=null){
            tradeSkuVOStr= JsonParseUtil.gson3.toJson(tradeSkuVOList);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("createVo",tradeSkuVOStr);
        BBCHttpUtil.postHttp(activity, Constants.getCouponsForBuy, param, CouponSettleBean.class, new LangHttpInterface<CouponSettleBean>() {
            @Override
            public void success(CouponSettleBean response) {
                model.setCouponSettleBean(response);
                model.notifyData("getCouponsForBuy");
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

    //获取订单金额信息
    public void reqSettlementAmount(List<TradeSkuVO> tradeSkuVOList, AddrBean addrBean, Coupon conpon, String groupId, boolean isEqualAddress, String tradeGroupCreateId){
        String tradeSkuVOStr="";
        if(tradeSkuVOList!=null){
            tradeSkuVOStr= JsonParseUtil.gson3.toJson(tradeSkuVOList);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("createVo",tradeSkuVOStr);
        if(addrBean!=null&&!BBCUtil.isEmpty(addrBean.getId())) {
            param.put("userAddrId", addrBean.getId());
        }
        if(conpon!=null&&conpon.getId()>0) {
            param.put("userCouponId", conpon.getId());
        }
        param.put("isEqualAddress",isEqualAddress);
        if (!BBCUtil.isEmpty(groupId)){
            param.put("groupId", groupId);
        }
        if (!BBCUtil.isEmpty(tradeGroupCreateId)){
            param.put("tradeGroupCreateId", tradeGroupCreateId);
        }
        BBCHttpUtil.postHttp(activity, Constants.getAmount, param, AmountSettleBean.class, new LangHttpInterface<AmountSettleBean>() {
            @Override
            public void success(AmountSettleBean response) {
                model.setAmountSettleBean(response);
                model.notifyData("reqSettlementAmount");
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

    //顶部物流信息
    public void reqParCode(){
        Map<String, Object> param = new HashMap<String, Object>();
//        BBCHttpUtil.postHttp(activity, Constants.REQ_PARCODE_CAR, param, TopNotifyBean.class, new LangHttpInterface<TopNotifyBean>() {
//            @Override
//            public void success(TopNotifyBean response) {
//                model.setTopNotifyBean(response);
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
//            }
//        });
    }

    //创建订单
    public void createOrder(Coupon useCoupon,boolean ifAnonymous,boolean ifUseSystemCash,String message,List<TradeSkuVO> tradeSkuVO,String userAddrId,String userIdCardId,String mobileNetId,Map<String,Object> groupParam,int takeType,int userGrade){
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(groupParam);
        if(useCoupon!=null){
            param.put("couponId",useCoupon.getId());
        }
        param.put("ifAnonymous",ifAnonymous);
        param.put("ifUseSystemCash",ifUseSystemCash);
        if(!BBCUtil.isEmpty(message)){
            param.put("message",message);
        }
        if(tradeSkuVO!=null&&tradeSkuVO.size()>0){
            String tradeSkuVOStr="";
            if(tradeSkuVO!=null){
                tradeSkuVOStr= JsonParseUtil.gson3.toJson(tradeSkuVO);
            }
            param.put("createVo",tradeSkuVOStr);

            //点击埋点普通结算页下单统计
            MobclickAgent.onEvent(activity,"settlement_normal",tradeSkuVOStr);
        }
        if(!BBCUtil.isEmpty(userAddrId)) {
            param.put("userAddrId", userAddrId);
        }
        if(!BBCUtil.isEmpty(userIdCardId)) {
            param.put("userIdCardId", userIdCardId);
        }
        if(!BBCUtil.isEmpty(mobileNetId)){
            param.put("mobileNetId",mobileNetId);
        }
        param.put("takeType",takeType);//收货方式 1立即发货 2云仓发货
        param.put("userGrade",userGrade);//购物等级
        BBCHttpUtil.postHttp(activity, Constants.CREATE_ORDER, param, CreateOrderBean.class, new LangHttpInterface<CreateOrderBean>() {
            @Override
            public void success(CreateOrderBean response) {
                model.setCreateOrderBean(response);
                model.notifyData("createOrder");
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

    //获取微信支付宝等预支付信息
    //1-支付宝扫码支付 2-支付宝pc端网页支付 3-支付宝h5网页支付 4-app调用支付宝支付 5-微信扫码支付 6-微信pc端网页支付 7-微信h5网页支付 8-app调起微信支付 9-微信内支付(使用微信公众号)
    public void reqPrePayDetail(String tradeNo,int payType){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeNo",tradeNo);
        param.put("payType",payType);
        BBCHttpUtil.postHttp(activity, Constants.POST_PAY, param, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String response) {
                model.setPayResult(response);
                model.notifyData("reqPrePayDetail");
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

    //购买礼包确认订单查询接口
    public void getForGift(String goodsId,String skuId,String addrId,String idCardId){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("goodsId",goodsId);
        param.put("skuId",skuId);
        if(!BBCUtil.isEmpty(addrId)){
            param.put("addrId",addrId);
        }
        if(!BBCUtil.isEmpty(idCardId)){
            param.put("idCardId",idCardId);
        }

//        BBCHttpUtil.postHttp(activity, Constants.GET_FORGIFT, param, PayModel.class, new LangHttpInterface<PayModel>() {
//            @Override
//            public void success(PayModel response) {
//                model.setIfNeedShowServiceAgreement(response.isIfNeedShowServiceAgreement());
//                model.setIfNeedCardId(response.isIfNeedCardId());
//                model.setSkuList(response.getSkuList());
//                model.setUserAddr(response.getUserAddr());
//                model.setUserIdcard(response.getUserIdcard());
//                model.setCouponAmount(response.getCouponAmount());
//                model.setGiftDes(response.getGiftDes());
//                model.setGiftRebateAmount(response.getGiftRebateAmount());
//                model.setReturnTitle(response.getReturnTitle());
//                model.setRealPayAmount(response.getRealPayAmount());
//                model.setSumAmount(response.getSumAmount());
//                model.setSumPostage(response.getSumPostage());
//                model.setTotalAmount(response.getTotalAmount());
//                model.setSumTax(response.getSumTax());
//                model.setBalancePayMsg(response.getBalancePayMsg());
//                model.setBalanceType(response.getBalanceType());
//                model.setCanUseCash(response.isCanUseCash());
//                model.setBottomPrompt(response.getBottomPrompt());
//                model.setIfLiantongCard(response.isIfLiantongCard());
//                model.setTaxDetails(response.getTaxDetails());
//                model.setScore(response.getScore());
//                model.setOrderDelayGoodsReminderList(response.getOrderDelayGoodsReminderList());
//                model.notifyData("getForGift");
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

    //创建礼包订单
    public void createGiftOrder(boolean ifUseSystemCash,String message,String goodsId,String skuId,String userAddrId,String userIdCardId,String mobileNetId){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ifUseSystemCash",ifUseSystemCash);
        if(!BBCUtil.isEmpty(message)){
            param.put("message",message);
        }
        param.put("goodsId",goodsId);
        param.put("skuId",skuId);

        //点击埋点礼包结算页下单统计
        MobclickAgent.onEvent(activity,"settlement_gift",goodsId);

        param.put("userAddrId",userAddrId);
        param.put("userIdCardId",userIdCardId);
        if(!BBCUtil.isEmpty(mobileNetId)){
            param.put("mobileNetId",mobileNetId);
        }
//        BBCHttpUtil.postHttp(activity, Constants.CREATE_GIFTORDER, param, CreateOrderBean.class, new LangHttpInterface<CreateOrderBean>() {
//            @Override
//            public void success(CreateOrderBean response) {
//                model.setCreateOrderBean(response);
//                model.notifyData("createGiftOrder");
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

    //订单去支付查询接口
    public void reqOrderToPay(String tradeNo){
        Map<String,Object> params=new HashMap<>();
        params.put("tradeNo",tradeNo);
        BBCHttpUtil.postHttp(activity, Constants.REQ_ORDERTOPAY, params, CreateOrderBean.class, new LangHttpInterface<CreateOrderBean>() {
            @Override
            public void success(CreateOrderBean response) {
                model.setCreateOrderBean(response);
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

    //支付宝微信付款时，查询订单交易情况 1->支付宝,2->微信,3->小程序
    public void payTradeQuery(int payWay,String tradeNo){
        if(BBCUtil.isEmpty(tradeNo)){
            return;
        }
        Map<String,Object> params=new HashMap<>();
        params.put("tradeNo",tradeNo);
        params.put("payWay",payWay);
//        BBCHttpUtil.postHttp(activity, Constants.REQ_TradeQuery, params, String.class, new LangHttpInterface<String>() {
//            @Override
//            public void success(String response) {
//                model.setPayResultStr(response);
//                model.notifyData("payTradeQuery");
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

    //手机号列表
    public void  reqMobileListByPage(boolean loadMore){
        if (!loadMore) {
            pageIndex = 1;
            haveMore = true;
        } else {
            haveMore = false;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNo", pageIndex);
        Type type= new TypeToken<BasePage<PhoneLTBean>>(){}.getType();
//        BBCHttpUtil.postHttp(activity, Constants.REQ_PHONELIST, params, type, new LangHttpInterface<BasePage<PhoneLTBean>>() {
//            @Override
//            public void success(BasePage<PhoneLTBean> response) {
//                if(model.getPhoneLTBeanList()==null){
//                    model.setPhoneLTBeanList(new ArrayList<PhoneLTBean>());
//                }
//                if (pageIndex == 1) {
//                    model.getPhoneLTBeanList().clear();
//                }
//                if(response.getList()!=null) {
//                    model.getPhoneLTBeanList().addAll(response.getList());
//                }
//                haveMore = response.isHasNextPage();
//                pageIndex++;
//                model.notifyData("reqMobileListByPage");
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

    //支付宝微信付款时，查询订单交易情况 1->支付宝,2->微信
    public void reqSaveOrUpdateNetInfo(String id,String name,String idCard,String mobile,String frontPhoto,String backPhoto,String idCardPhoto){
        Map<String,Object> params=new HashMap<>();
        if(!BBCUtil.isEmpty(id)){
            params.put("id",id);
        }
        params.put("name",name);
        params.put("idCard",idCard);
        params.put("mobile",mobile);
        params.put("frontPhoto",frontPhoto);
        params.put("backPhoto",backPhoto);
        params.put("idCardPhoto",idCardPhoto);

//        BBCHttpUtil.postHttp(activity, Constants.REQ_SAVEUPDATE_PHONE, params, PhoneLTBean.class, new LangHttpInterface<PhoneLTBean>() {
//            @Override
//            public void success(PhoneLTBean response) {
//                model.setSelectPhoneLTBean(response);
//                model.notifyData("reqSaveOrUpdateNetInfo");
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

    //查询入网信息
    public void reqQueryMobileNetById(String id){
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);

//        BBCHttpUtil.postHttp(activity, Constants.REQ_MOBILENETBYID, params, PhoneLTDetailBean.class, new LangHttpInterface<PhoneLTDetailBean>() {
//            @Override
//            public void success(PhoneLTDetailBean response) {
//                model.setPhoneLTDetailBean(response);
//                model.notifyData("reqQueryMobileNetById");
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

    //获取微信支付是否使用小程序
    public void reqPreWXPay(String tradeNo){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeNo",tradeNo);
//        BBCHttpUtil.postHttp(activity, Constants.POST_PAY_WX, param, WxPrePayBean.class, new LangHttpInterface<WxPrePayBean>() {
//            @Override
//            public void success(WxPrePayBean response) {
//                model.setWxPrePayBean(response);
//                model.notifyData("reqPreWXPay");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    //图片上传
    public void uploadImage(BaseLangActivity context, File file, String serverUrl, final boolean isUpHeadImg, String type){
//        BBCHttpUtil.upImage(context, file, serverUrl, isUpHeadImg, type, true,new LangImageUpInterface() {
//            @Override
//            public void success(String resp) {
//                model.setUpImgUrl(resp);
//                model.notifyData("uploadImage");
//            }
//        });
    }

    //一键开团
    public void reqChangeTradeGroup(String tradeNo){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeNo",tradeNo);
//        BBCHttpUtil.postHttp(activity, Constants.REQ_CHANGE_TRADE_GROUP, param, ChangeTradeBean.class, new LangHttpInterface<ChangeTradeBean>() {
//            @Override
//            public void success(ChangeTradeBean response) {
//                if(response!=null){
//                    model.setChangeTradeNo(response.getTradeNo());
//                }
//                model.notifyData("reqChangeTradeGroup");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    //团支付验证
    public void reqGroupPayCheck(String tradeNo){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeNo",tradeNo);
//        BBCHttpUtil.postHttp(activity, Constants.REQ_GROUP_PAY_CHECK, param, String.class, new LangHttpInterface<String>() {
//            @Override
//            public void success(String response) {
//                model.setPayResult(response);
//                model.notifyData("reqGroupPayCheck");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }
}
