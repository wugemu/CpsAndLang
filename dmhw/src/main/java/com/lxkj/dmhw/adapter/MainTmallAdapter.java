package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

public class MainTmallAdapter extends BaseQuickAdapter<CommodityList.CommodityData, BaseViewHolder> {

    private Context context;

    public MainTmallAdapter(Context context) {
        super(R.layout.adapter_main_tmall);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper,CommodityList.CommodityData item) {
        try {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) helper.getView(R.id.adapter_voucher_layout).getLayoutParams();
            switch (helper.getLayoutPosition()) {
                case 0:
                    params.setMargins(0, 0, Utils.dipToPixel(R.dimen.dp_10), 0);
                    break;
                default:
                    params.setMargins(0, 0, Utils.dipToPixel(R.dimen.dp_10), 0);
                    break;
            }
            Utils.displayImageRounded(context, item.getShopmainpic(), helper.getView(R.id.adapter_tmall_image), 5);
            helper.setText(R.id.adapter_tmall_title, item.getShopname());
            helper.setText(R.id.adapter_after_voucher_money, item.getMoney());//券后价
            helper.setText(R.id.adapter_voucher_money, item.getCoupondenomination()+"");//优惠券
            helper.getView(R.id.adapter_voucher_layout).setOnClickListener(v -> context. startActivity(new Intent(context, CommodityActivity290.class).putExtra("shopId", item.getShopid()).putExtra("source", "dmj").putExtra("sourceId","")));
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
