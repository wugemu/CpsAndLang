package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

public class BigBrandListItemAdapter extends BaseQuickAdapter<CommodityDetails290, BaseViewHolder> {

    private Context context;

    public BigBrandListItemAdapter(Context context) {
        super(R.layout.adapter_brand_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper,CommodityDetails290 item) {
        try {
            Utils.displayImageRounded(context, item.getImageUrl(), helper.getView(R.id.adapter_goods_image), 5);
            helper.setText(R.id.adapter_goods_title, item.getName());
            helper.setText(R.id.adapter_after_voucher_money, item.getPrice());//券后价
            helper.getView(R.id.adapter_voucher_layout).setOnClickListener(v ->
            context.startActivity(new Intent(context, CommodityActivity290.class).putExtra("shopId", item.getId()).putExtra("source", item.getSource()).putExtra("sourceId", item.getSourceId())));
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
