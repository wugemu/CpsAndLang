package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.bean.Voucher;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

public class VoucherAdapter extends BaseQuickAdapter<Voucher.VoucherData, BaseViewHolder> {

    private Context context;

    public VoucherAdapter(Context context) {
        super(R.layout.adapter_voucher);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Voucher.VoucherData item) {
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
            Utils.displayImageRounded(context, item.getShopmainpic(), helper.getView(R.id.adapter_voucher_image), 5);
            helper.setText(R.id.adapter_voucher_tips, Utils.getNumber(item.getShopmonthlysales()) + "人已领 | " + item.getDiscount() + "元券");
            helper.setText(R.id.adapter_voucher_title, item.getShopname());
            helper.setText(R.id.adapter_voucher_money, item.getMoney());
            ((TextView) helper.getView(R.id.adapter_voucher_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            helper.setText(R.id.adapter_voucher_price, item.getShopprice());
            helper.getView(R.id.adapter_voucher_layout).setOnClickListener(v ->  context. startActivity(new Intent(context, CommodityActivity290.class).putExtra("shopId", item.getShopid()).putExtra("source", "dmj").putExtra("sourceId","")));
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
