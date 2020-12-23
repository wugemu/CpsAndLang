package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.fastjson.JSON;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MorePlSearchListActivity;
import com.lxkj.dmhw.bean.Alibc;
import com.lxkj.dmhw.bean.CommodityDetails;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.Coupon;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.PJWLink;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.SimpleSearchDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

/**
 * 一键转链
 */

public class ChainGoodsDialog extends SimpleSearchDialog<String> implements AlibcTradeCallback {


    ImageView image;
    TextView title;
    TextView discount;
    TextView estimate;
    TextView price,bijiaTxt;
    CommodityDetails290 commodityDetails;
    String type;
    HashMap<String, String> paramMap = new HashMap<>();
    private static ChainGoodsDialog mInstance;
    //同一个商品页面 点击“我知道了” 不再显示弹框
    private Boolean isshowDialog=false;
    String shopId="";
    String source="";
    String sourceId="";
    String couponId="";
    String couponLink="";
    TextPaint tp;
    /**
     * 初始化ord
     * @param context  上下文
     * @param data     数据
     */

    public ChainGoodsDialog(Context context, String data) {
        super(context, R.layout.dialog_chain, data, true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        helper.setOnClickListener(R.id.lyaout000, this);
        helper.setOnClickListener(R.id.lyaout001, this);
        helper.setOnClickListener(R.id.close_txt_layout, this);
        helper.setOnClickListener(R.id.get_buy_layout, this);
        helper.setOnClickListener(R.id.detail_layout, this);
        image=helper.getView(R.id.image);
        title=helper.getView(R.id.title);
        discount=helper.getView(R.id.discount);
        estimate=helper.getView(R.id.estimate_text);
        price=helper.getView(R.id.price);
        bijiaTxt=helper.getView(R.id.bijia);
        TextPaint tp=title.getPaint();
        tp.setFakeBoldText(true);
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {
            case R.id.close_txt_layout:
                hideDialog();
                Variable.AdvertiseShowStatus=true;
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
                break;
            case R.id.lyaout000:
                break;
            case R.id.lyaout001:
                break;
            case R.id.get_buy_layout:
                //购买
                if (!DateStorage.getLoginStatus()){
                    intent=new Intent(context, LoginActivity.class);
                }else {
                    switch (type) {
                        case "tb":
                        case "tm":
                            //获取高佣接口
                            paramMap.clear();
                            paramMap.put("shopid", shopId);
                            paramMap.put("source", source);
                            paramMap.put("sourceId", sourceId);
                            paramMap.put("couponid", couponId);
                            paramMap.put("tpwd", "");
                            paramMap.put("sharelink", "");
                            NetworkRequest.getInstance().POST(handlerdialog, paramMap, "GoodsPromotion", HttpCommon.GoodsPromotion);
                            break;
                        case "pdd":
                            paramMap.clear();
                            paramMap.put("goodsId", shopId);
                            paramMap.put("needAuth", true+"");
                            NetworkRequest.getInstance().GETNew(handlerdialog, paramMap, "GenByGoodsId", HttpCommon.PDDGenByGoodsId);
                            break;
                        case "jd":
                            paramMap.clear();
                            paramMap.put("goodsId", shopId);
                            paramMap.put("couponLink", couponLink);
                            NetworkRequest.getInstance().GETNew(handlerdialog, paramMap, "GenByGoodsId", HttpCommon.JDGenByGoodsId);
                            break;
                        case "wph":
                            paramMap.clear();
                            paramMap.put("goodsId", shopId);
                            NetworkRequest.getInstance().GETNew(handlerdialog, paramMap, "GenByGoodsId", HttpCommon.WPHGenByGoodsId);
                            break;
                        case "sn":
                            break;
                        case "kl":
                            break;
                    }
                }
                hideDialog();
                ClearClib();
                break;
            case R.id.detail_layout:
                    switch (type) {
                        case "tb":
                        case "tm":
                            intent=new Intent(context, CommodityActivity290.class).putExtra("shopId", commodityDetails.getId()).putExtra("source", commodityDetails.getSource()).putExtra("sourceId", commodityDetails.getSourceId());
                            break;
                        case "pdd":
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", commodityDetails.getId()).putExtra("type", "pdd");
                            break;
                        case "jd":
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", commodityDetails.getId()).putExtra("type", "jd");
                            break;
                        case "wph":
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", commodityDetails.getId()).putExtra("type", "wph");
                            break;
                        case "sn":
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", commodityDetails.getId()).putExtra("type", "sn");
                            break;
                        case "kl":
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", commodityDetails.getId()).putExtra("type", "kl");
                            break;
                    }
                    hideDialog();
                    ClearClib();
                    Variable.AdvertiseShowStatus=true;
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
                break;
        }
        if (intent!=null){
            startActivity(intent);
        }
    }

    public void showDialog(CommodityDetails290 commodityDetails290,String content) {
        if(content==null||content.length()==0){
            return;
        }
        DateStorage.setClip(content);
        commodityDetails=commodityDetails290;
        type=commodityDetails.getType();
        shopId=commodityDetails.getId();
        source=commodityDetails.getSource();
        sourceId=commodityDetails.getSourceId();
        tp=discount.getPaint();
        tp.setFakeBoldText(true);
        tp=estimate.getPaint();
        tp.setFakeBoldText(true);
        Object couponObject = JSON.parseObject(commodityDetails290.getCouponInfo(), Coupon.class);
        Coupon coupon = (Coupon) couponObject;
        if (coupon!=null&&coupon.getId()!=null){
            couponId=coupon.getId();
        }
        if (coupon!=null&&coupon.getLink()!=null){
            couponLink=coupon.getLink();
        }
        //比价显示
        if (commodityDetails.getIsbj().equals("1")){
            bijiaTxt.setVisibility(View.VISIBLE);
            title.setText("         "+commodityDetails290.getName());
        }else{
            bijiaTxt.setVisibility(View.GONE);
            title.setText(commodityDetails290.getName());
        }
        Utils.displayImage(context,commodityDetails290.getImageUrl(), image);
        Object CpsTypeObject = JSON.parseObject(commodityDetails290.getCpsType(), CpsType.class);
        CpsType cpsType = (CpsType) CpsTypeObject;
        if (cpsType.getCode().equals("wph")||cpsType.getCode().equals("kl")){
            if (commodityDetails290.getSave().equals("")){
                discount.setText("0元");
            }else{
                discount.setText(commodityDetails290.getSave()+"元");
            }
        }else{
            if (commodityDetails290.getCoupon().equals("")){
            discount.setText("0元");
            }else{
             discount.setText(commodityDetails290.getCoupon()+"元");
            }
        }
        estimate.setText("奖"+commodityDetails290.getNormalCommission());
        price.setText(commodityDetails290.getPrice());
        if (!isDialog()) {
            showDialog();
        }
    }

    //清空剪切板
    private void ClearClib(){
        try{
            ClipboardManager manager = (ClipboardManager) MyApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            assert manager != null;
            manager.setPrimaryClip(ClipData.newPlainText(null, ""));
            DateStorage.setClip("");
        }catch (Exception e){

        }
    }


    Handler handlerdialog = new Handler(message -> {
        try {
            if (message.what == LogicActions.Failed) {
                ToastUtil.showImageToast(context,message.obj.toString(),R.mipmap.toast_error);
            }
            //处理淘宝高佣
            if (message.what == LogicActions.GoodsPromotionSuccess) {
                Alibc alibc = (Alibc) message.obj;
                String Couponlink = alibc.getCouponlink();
                String pid = alibc.getPid();
                if (alibc.getTips().getType().equals("hb")) {
                    //红包
                    if (alibc.getTips().getKey().equals("")) {
                        DateStorage.setIsKey("");
                        Utils.Alibc(context, Couponlink, pid, this);
                        Variable.AdvertiseShowStatus=true;
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
                    } else {
                        if (alibc.getTips().getKey().equals(DateStorage.getIsKey()) || isshowDialog) {
                            //已经点击了不再提醒
                            Utils.Alibc(context, Couponlink, pid, this);
                            Variable.AdvertiseShowStatus=true;
                            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
                        } else {
                            try {
                                CouponLinkDialog couponLinkDialog = new CouponLinkDialog(context, alibc.getTips());
                                couponLinkDialog.showDialog();
                                couponLinkDialog.setOnClickListener(new CouponLinkDialog.OnBtnClickListener() {
                                    @Override
                                    public void onClick(int pos) {
                                        if (pos == 0) {//不再提醒
                                            DateStorage.setIsKey(alibc.getTips().getKey());
                                        } else {
                                            isshowDialog = true;
                                        }
                                        Utils.Alibc(context, Couponlink, pid, ChainGoodsDialog.this );
                                        Variable.AdvertiseShowStatus=true;
                                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
                                    }
                                });
                            } catch (Exception e) {

                            }

                        }
                    }
                } else {
                    //比价
                    if (!DateStorage.getHasBj().equals("")) {
                        Utils.Alibc(context, Couponlink, pid, this);
                        Variable.AdvertiseShowStatus=true;
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
                    } else {
                        try {
                            BiJiaDialog biJiaDialog = new BiJiaDialog(context, alibc.getTips());
                            biJiaDialog.showDialog();
                            biJiaDialog.setOnClickListener(new BiJiaDialog.OnBtnClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    if (pos == 0) {//不再提醒
                                        DateStorage.setHasBj("bj");
                                        Utils.Alibc(context, Couponlink, pid, ChainGoodsDialog.this);
                                    } else if (pos == 1) {
                                        Utils.Alibc(context, Couponlink, pid, ChainGoodsDialog.this);
                                    } else {
                                        Intent intent = new Intent(context, AliAuthWebViewActivity.class);
                                        intent.putExtra(Variable.webUrl, alibc.getTips().getRuleurl());
                                        intent.putExtra("isTitle", true);
                                        startActivity(intent);
                                    }
                                    Variable.AdvertiseShowStatus=true;
                                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
                                }
                            });
                        } catch (Exception e) {

                        }
                    }

                }
            }

            //处理拼京唯
            if (message.what == LogicActions.GenByGoodsIdSuccess) {
                PJWLink link = (PJWLink) message.obj;
                if (Utils.isNullOrEmpty(link.getShortUrl())) {
                    Toast.makeText(context, "URL为空", Toast.LENGTH_SHORT).show();
                } else {
                    int plat = 1;
                    if (type.equals("jd")) {
                        plat = 2;
                    } else if (type.equals("pdd")) {
                        plat = 1;
                    } else if (type.equals("wph")) {
                        //唯品会
                        plat = 3;
                    }
                    Utils.callMorePlatFrom(context, plat, link.getSchemaUrl(), link.getUrl());
                    dismiss();
                }
            }
        } catch (Exception e) {
            Logger.e(e, "回调信息");
        }
        return false;
    });

    @Override
    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

    }

    @Override
    public void onFailure(int i, String s) {

    }
}
