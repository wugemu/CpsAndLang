package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.bean.Popular;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

public class PopularAdapter extends BaseQuickAdapter<Popular.PopularData, BaseViewHolder> {

    private Context context;

    public PopularAdapter(Context context) {
        super(R.layout.adapter_popular);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Popular.PopularData item) {
        try {
            Utils.displayImageRounded(context, item.getShopmainpic(), helper.getView(R.id.adapter_popular_image), 5);
            if (Objects.equals(item.getPlatformtype(), "天猫")) {
                helper.setImageResource(R.id.adapter_popular_check, R.mipmap.shop_list_tmall);
            } else {
                helper.setImageResource(R.id.adapter_popular_check, R.mipmap.shop_list_taobao);
            }
            helper.setText(R.id.adapter_popular_title, item.getShopname());
            helper.setText(R.id.adapter_popular_time, item.getSenddate());
            helper.setText(R.id.adapter_popular_money, item.getPostcouponprice());
            helper.setText(R.id.adapter_popular_estimate_text, item.getPrecommission());
            UserInfo login = DateStorage.getInformation();
            if (DateStorage.getLoginStatus()) {
                if (Objects.equals(login.getUsertype(), "3")){
                    helper.setGone(R.id.adapter_popular_estimate, false);
                } else {
                    helper.setGone(R.id.adapter_popular_estimate, true);
                }
            } else {
                helper.setGone(R.id.adapter_popular_estimate, false);
            }
            helper.getView(R.id.adapter_popular_layout).setOnClickListener(v ->context. startActivity(new Intent(context, CommodityActivity290.class).putExtra("shopId", item.getShopid()).putExtra("source", "tb").putExtra("sourceId","")));
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

}
