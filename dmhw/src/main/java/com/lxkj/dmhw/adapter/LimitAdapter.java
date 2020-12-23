package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Limit;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

/**
 * 抢购商品
 * Created by Android on 2018/7/27.
 */

public class LimitAdapter extends BaseQuickAdapter<Limit.LimitData, BaseViewHolder> {

    private Activity activity;
    private int tips = 0;
    TextPaint tp;
    public LimitAdapter(Activity activity, int tips) {
        super(R.layout.adapter_limit);
        this.activity = activity;
        this.tips = tips;
    }

    @Override
    protected void convert(BaseViewHolder helper, Limit.LimitData item) {
        try {
            TextView adapter_limit_tips_one=helper.getView(R.id.adapter_limit_tips_one);
            tp= adapter_limit_tips_one.getPaint();
            tp.setFakeBoldText(true);
            if (helper.getLayoutPosition()== 0) {//第一项
                helper.setBackgroundColor(R.id.transparent_one, Color.parseColor("#00000000"));
                helper.setBackgroundColor(R.id.transparent_two, Color.parseColor("#f4f4f4"));
            }else{
                helper.setBackgroundColor(R.id.transparent_one, Color.parseColor("#f4f4f4"));
                helper.setBackgroundColor(R.id.transparent_two, Color.parseColor("#f4f4f4"));
            }
            TextView adapter_limit_buy=helper.getView(R.id.adapter_limit_buy);
            switch (tips) {
                case 0:
                    adapter_limit_buy.setPadding(Utils.dipToPixel(R.dimen.dp_6),0,Utils.dipToPixel(R.dimen.dp_6),0);
                    helper.setVisible(R.id.adapter_limit_tips_layout, false);
                    helper.setBackgroundRes(R.id.adapter_limit_buy_layout,R.drawable.adapter_brand_long_bg);
                    helper.setText(R.id.adapter_limit_buy, "已抢 "+item.getShopmonthlysales());
                    if (Objects.equals(item.getProgress(), item.getCouponcount())) {
                     helper.setVisible(R.id.adapter_limit_tips_layout, true);
                     helper.setText(R.id.adapter_limit_tips_one, "已抢光");
                     helper.setText(R.id.adapter_limit_tips_two, "sold out");
                    }
                    break;
                case 1:
                    adapter_limit_buy.setPadding(Utils.dipToPixel(R.dimen.dp_15),0,Utils.dipToPixel(R.dimen.dp_15),0);
                    helper.setVisible(R.id.adapter_limit_tips_layout, true);
                    helper.setText(R.id.adapter_limit_tips_one, "即将开始");
                    helper.setText(R.id.adapter_limit_tips_two, "about to begin");
                    helper.setText(R.id.adapter_limit_buy, "即将开始");
                    helper.setBackgroundRes(R.id.adapter_limit_buy_layout,0);
                    break;
                case 2:
                    adapter_limit_buy.setPadding(Utils.dipToPixel(R.dimen.dp_15),0,Utils.dipToPixel(R.dimen.dp_15),0);
                    helper.setVisible(R.id.adapter_limit_tips_layout, true);
                    helper.setText(R.id.adapter_limit_tips_one, "明日开始");
                    helper.setText(R.id.adapter_limit_tips_two, "tomorrow to begin");
                    helper.setText(R.id.adapter_limit_buy, "明日开始");
                    helper.setBackgroundRes(R.id.adapter_limit_buy_layout,0);
                    break;
            }
//            if (Objects.equals(item.getProgress(), item.getCouponcount())) {
//                helper.setVisible(R.id.adapter_limit_tips_layout, true);
//                helper.setText(R.id.adapter_limit_tips_one, "已抢光");
//                helper.setText(R.id.adapter_limit_tips_two, "sold out");
//            } else {
//                switch (tips) {
//                    case 0:
//                        helper.setVisible(R.id.adapter_limit_tips_layout, false);
//                        helper.setBackgroundRes(R.id.adapter_limit_buy_layout,R.drawable.adapter_brand_long_bg);
//                        break;
//                    case 1:
//                        helper.setVisible(R.id.adapter_limit_tips_layout, true);
//                        helper.setText(R.id.adapter_limit_tips_one, "即将开始");
//                        helper.setText(R.id.adapter_limit_tips_two, "about to begin");
//                        helper.setText(R.id.adapter_limit_buy, "即将开始");
//                        helper.setBackgroundRes(R.id.adapter_limit_buy_layout,0);
//                        break;
//                    case 2:
//                        helper.setVisible(R.id.adapter_limit_tips_layout, true);
//                        helper.setText(R.id.adapter_limit_tips_one, "明日开始");
//                        helper.setText(R.id.adapter_limit_tips_two, "tomorrow to begin");
//                        helper.setText(R.id.adapter_limit_buy, "明日开始");
//                        helper.setBackgroundRes(R.id.adapter_limit_buy_layout,0);
//                        break;
//                }
//            }
            TextView adapter_limit_title=helper.getView(R.id.adapter_limit_title);
            TextPaint tp;
            tp = adapter_limit_title.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageRounded(activity, item.getShopmainpic(), helper.getView(R.id.adapter_limit_image), 5);
            helper.setText(R.id.adapter_limit_title, item.getShopname());
            helper.getView(R.id.adapter_limit_check).setVisibility(View.GONE);
            helper.setText(R.id.adapter_limit_estimate, item.getPrecommission());
            helper.setText(R.id.adapter_limit_money, item.getMoney());
            helper.setText(R.id.adapter_limit_discount, item.getDiscount() + "元");
            UserInfo login = DateStorage.getInformation();
            if (DateStorage.getLoginStatus()) {
                if (Objects.equals(login.getUsertype(), "3")){
                    helper.setVisible(R.id.adapter_limit_estimate_layout, false);
                } else {
                    helper.setVisible(R.id.adapter_limit_estimate_layout, true);
                }
            } else {
                helper.setVisible(R.id.adapter_limit_estimate_layout, false);
            }
        } catch (NumberFormatException e) {
            Logger.e(e, "");
        }
    }
}
