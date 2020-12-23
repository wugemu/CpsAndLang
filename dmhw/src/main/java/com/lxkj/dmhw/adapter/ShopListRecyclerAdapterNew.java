package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.text.TextPaint;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

/**
 * 商品列表
 * Created by Zyhant on 2018/1/12.
 */

public class ShopListRecyclerAdapterNew extends BaseQuickAdapter<CommodityList.CommodityData, BaseViewHolder> {

    private Activity activity;
    TextPaint tp;
    public ShopListRecyclerAdapterNew(Activity activity) {
        super(R.layout.adapter_list_shop_linear_raudis);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityList.CommodityData item) {
        try {
            if (item.getIsFirstNetwork()==1){
                helper.setGone(R.id.network_layout, true);
            }else{
                helper.setGone(R.id.network_layout, false);
            }
            TextView adapter_new_one_fragment_discount=helper.getView(R.id.adapter_new_one_fragment_discount);
            tp=adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_estimate_text=helper.getView(R.id.adapter_new_one_fragment_estimate_text);
            tp=adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageRounded(activity, item.getShopmainpic(), helper.getView(R.id.adapter_new_one_fragment_image), 5);
            helper.setText(R.id.adapter_new_one_fragment_title, ""+item.getTitle());
            helper.setText(R.id.adapter_new_one_fragment_discount, item.getDiscount()+"元");
            helper.setText(R.id.adapter_new_one_fragment_number, "已售" +Utils.getNumber(item.getShopmonthlysales()));
            helper.setText(R.id.adapter_new_one_fragment_estimate_text,  "奖 ¥"+item.getPrecommission());
            helper.setText(R.id.adapter_new_one_fragment_shop, item.getSellername());
            helper.setText(R.id.adapter_new_one_fragment_original_price,  "原价¥"+item.getShopprice());
            TextView adapter_new_one_fragment_price =helper.getView(R.id.adapter_new_one_fragment_price);
            TextView rmb =helper.getView(R.id.rmb);
            tp=adapter_new_one_fragment_price.getPaint();
            tp.setFakeBoldText(true);
            tp=rmb.getPaint();
            tp.setFakeBoldText(true);
            adapter_new_one_fragment_price.setText(item.getMoney());
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
            if (item.isCheck()) {
                helper.setImageResource(R.id.adapter_new_one_fragment_check, R.mipmap.shop_list_tmall);
            } else {
                helper.setImageResource(R.id.adapter_new_one_fragment_check, R.mipmap.shop_list_taobao);
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
