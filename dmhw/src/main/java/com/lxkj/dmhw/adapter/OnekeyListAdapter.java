package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.Coupon;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.ShopInfo290;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 一键转链商品列表
 */

public class OnekeyListAdapter extends BaseQuickAdapter<CommodityDetails290, BaseViewHolder> {

    private Activity activity;

    private OnClickListener onClickListener;

    //平台类型
    private CpsType cpsType=new CpsType();
    //优惠券
    private Coupon coupon=new Coupon();
    private ArrayList<String> ImageBanerList = new ArrayList<>();
    private TextPaint tp;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnekeyListAdapter(Activity activity) {
        super(R.layout.adapter_onekey_list);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityDetails290 item) {
        try {
            TextView onekey_title= helper.getView(R.id.onekey_title);
            tp=onekey_title.getPaint();
            tp.setFakeBoldText(true);
               //优惠券
                Object CouponObject = JSON.parseObject(item.getCouponInfo(), Coupon.class);
                coupon = (Coupon) CouponObject;
                //获取平台类型
                Object CpsTypeObject = JSON.parseObject(item.getCpsType(), CpsType.class);
                cpsType = (CpsType) CpsTypeObject;
                if (cpsType.getCode().equals("wph")||cpsType.getCode().equals("kl")){
                    helper.setText(R.id.onekey_after_price_txt,"折后价  ");
                    helper.setBackgroundRes(R.id.coupon_layout,R.mipmap.wph_coupon_bg);
                    helper.setGone( R.id.onekey_number,false);
                    helper.setText(R.id.onekey_discount,item.getSave());
                }else{
                    helper.setText(R.id.onekey_after_price_txt,"券后  ");
                    helper.setBackgroundRes(R.id.coupon_layout,R.mipmap.coponbg);
                    helper.setGone( R.id.onekey_number,true);
                    if (coupon != null) {
                        if (coupon.getPrice().equals("")){
                            helper.setText(R.id.onekey_discount,"0");
                        }else {
                            helper.setText(R.id.onekey_discount,coupon.getPrice());
                        }
                    }else{
                        helper.setText(R.id.onekey_discount,"0");
                    }
                }
                Utils.displayImageRounded(activity, item.getImageUrl(),helper.getView(R.id.onekey_image),5);
                helper.setText(R.id.onekey_title,item.getName());
                helper.setText(R.id.onekey_after_price,item.getPrice());
                if (!item.getSales().equals("")){
                    helper.setText( R.id.onekey_number,"已售" + item.getSales());
                }else{
                    helper.setText( R.id.onekey_number,"已售" +0);
                }
            helper.setText(R.id.onekey_original_price,"原价  ¥" + item.getCost());
                if (cpsType != null) {
                    Utils.displayImage(activity, cpsType.getLogo(), helper.getView(R.id.type_img));
                }
            helper.setText(R.id.onekey_share_txt,"分享赚¥" + item.getNormalCommission());
            helper.setText(R.id.onekey_up_txt,"购买省¥" + item.getBuySave());

            helper.getView(R.id.onekey_share_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 onClickListener.OnShareClick(item);
                }
            });
            helper.getView(R.id.onekey_up_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnBuyClick(item);
                }
            });
            } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 点击事件
     */
    public interface OnClickListener {
        void OnShareClick(CommodityDetails290 commodityDetails290);
        void OnBuyClick(CommodityDetails290 commodityDetails290);
    }
}
