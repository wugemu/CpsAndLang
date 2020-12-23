package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.LimitActivity;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

public class LimitTimeAdapter extends BaseQuickAdapter<HomePage.LimitTimeList.LimitTimeGoods, BaseViewHolder> {

    private Context context;
    private int msize;
    private String time;
    TextPaint tp;
    public LimitTimeAdapter(Context context,int size,String tt) {
        super(R.layout.adapter_limit_time);
        this.context = context;
        this.msize=size;
        this.time=tt;
    }

    @Override
    protected void convert(BaseViewHolder helper,HomePage.LimitTimeList.LimitTimeGoods item) {
        try {
            RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(helper.getView(R.id.adapter_voucher_layout).getLayoutParams());
            layoutParams.width=msize;
            if (helper.getAdapterPosition()==0){
            layoutParams.setMargins(0,0,0,0);
            }else{
             layoutParams.setMargins(Utils.dipToPixel(R.dimen.dp_10),0,0,0);
            }
            helper.getView(R.id.adapter_voucher_layout).setLayoutParams(layoutParams);
            TextView textView=helper.getView(R.id.adapter_tmall_title);
            tp= textView.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_after_voucher_money=helper.getView(R.id.adapter_after_voucher_money);
            tp= adapter_after_voucher_money.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageRounded(context, item.getShopmainpic(), helper.getView(R.id.adapter_tmall_image), 5);
            helper.setText(R.id.adapter_tmall_title, item.getShopname());
            helper.setText(R.id.adapter_after_voucher_money, Utils.getFormatePrice(item.getDisprice()));//券后价
            helper.getView(R.id.adapter_voucher_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LimitActivity.class);
                    intent.putExtra("time",time);
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
