package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.HotSellFragmentActivity;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;


public class HotSellAdapter extends BaseQuickAdapter<HomePage.HotSell, BaseViewHolder> {

    private Activity activity;
    private int layoutSize;
    public HotSellAdapter(Activity activity, int size) {
        super(R.layout.adapter_hotsell);
        this.activity = activity;
        this.layoutSize=size;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePage.HotSell item) {
        try {
            RecyclerView.LayoutParams layoutParams= new RecyclerView.LayoutParams(helper.getView(R.id.adapter_hotsell_layout).getLayoutParams());
            layoutParams.width=layoutSize;
            if (helper.getAdapterPosition()==0){
                layoutParams.setMargins(0,0,0,0);
            }else{
                layoutParams.setMargins(Utils.dipToPixel(R.dimen.dp_10),0,0,0);
            }
            helper.getView(R.id.adapter_hotsell_layout).setLayoutParams(layoutParams);
            Utils.displayImageRounded(activity, item.getShopmainpic(), helper.getView(R.id.hotsell_recycler_image), 5);
            helper.setText(R.id.adapter_after_voucher_money, item.getPrice());
            helper.setText(R.id.hotsell_recycler_coupon, item.getCoupondenomination());
            helper.getView(R.id.adapter_hotsell_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转热销榜单
                   Intent intent = new Intent(activity, HotSellFragmentActivity.class);
                   activity.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
