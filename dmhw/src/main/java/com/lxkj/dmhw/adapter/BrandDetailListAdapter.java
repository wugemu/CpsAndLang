package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.ShopInfo;
import com.lxkj.dmhw.bean.ShopInfo290;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

/**
 * 品牌商品列表
 */

public class BrandDetailListAdapter extends BaseQuickAdapter<CommodityDetails290, BaseViewHolder> {

    private Activity activity;
    TextPaint tp;
    public BrandDetailListAdapter(Activity activity) {
        super(R.layout.adapter_list_bigbrand);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityDetails290 item) {
        try {
            if (helper.getLayoutPosition()== 0) {//第一项
                helper.setBackgroundColor(R.id.transparent_one, Color.parseColor("#00000000"));
            }else{
                helper.setBackgroundColor(R.id.transparent_one, Color.parseColor("#f4f4f4"));
            }
            TextView adapter_new_one_fragment_discount=helper.getView(R.id.adapter_new_one_fragment_discount);
            tp=adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_estimate_text=helper.getView(R.id.adapter_new_one_fragment_estimate_text);
            tp=adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageRoundedAll(activity, item.getImageUrl(), helper.getView(R.id.adapter_new_one_fragment_image),5);
            helper.setText(R.id.adapter_new_one_fragment_title, item.getName());
            helper.setText(R.id.adapter_new_one_fragment_discount, item.getCoupon()+"元");
            TextView adapter_new_one_fragment_price =helper.getView(R.id.adapter_new_one_fragment_price);
            tp=adapter_new_one_fragment_price.getPaint();
            tp.setFakeBoldText(true);
            TextView rmb =helper.getView(R.id.rmb);
            tp=rmb.getPaint();
            tp.setFakeBoldText(true);
            adapter_new_one_fragment_price.setText(item.getPrice());
            helper.setText(R.id.adapter_new_one_fragment_number, "已售" + item.getSales());
            helper.setText(R.id.adapter_new_one_fragment_estimate_text,  "奖 ¥"+item.getNormalCommission());
            helper.setText(R.id.adapter_new_one_fragment_original_price, "原价¥"+ item.getCost());
            UserInfo login = DateStorage.getInformation();
            if (DateStorage.getLoginStatus()) {
                if (Objects.equals(login.getUsertype(), "3")){
                    helper.setGone(R.id.adapter_new_one_fragment_estimate, false);
                } else {
                    helper.setGone(R.id.adapter_new_one_fragment_estimate, true);
                }
            } else {
                helper.setGone(R.id.adapter_new_one_fragment_estimate, false);
            }
            Object CpsTypeObject = JSON.parseObject(item.getCpsType(), CpsType.class);
            CpsType cpsType=(CpsType) CpsTypeObject;
            Utils.displayImageRounded(activity, cpsType.getLogo(), helper.getView(R.id.adapter_new_one_fragment_check), 2);
            Object shopTypeObject = JSON.parseObject(item.getShopInfo(), ShopInfo290.class);
            ShopInfo290 shopType=(ShopInfo290) shopTypeObject;
            helper.setText(R.id.adapter_new_one_fragment_shop,shopType.getName());

        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
