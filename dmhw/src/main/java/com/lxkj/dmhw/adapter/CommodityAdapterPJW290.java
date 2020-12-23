package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MainBottomListItem;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 猜你喜欢
 * Created by Administrator on 2017/12/21 0021.
 */

public class CommodityAdapterPJW290 extends BaseQuickAdapter<MainBottomListItem, BaseViewHolder> {

    private Context context;
    TextPaint tp;

    public CommodityAdapterPJW290(Context context) {
        super(R.layout.adapter_commodity);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (super.getItemCount()>=6) {
            return 6;
        }else{
           return super.getItemCount();
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MainBottomListItem item) {
        try {
            TextView quan_title= helper.getView(R.id.quan_title);
            tp=quan_title.getPaint();
            tp.setFakeBoldText(true);
            TextView quanhou_title= helper.getView(R.id.quanhou_title);
            tp=quanhou_title.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_commodity_money= helper.getView(R.id.adapter_commodity_money);
            tp=adapter_commodity_money.getPaint();
            tp.setFakeBoldText(true);

            Utils.displayImageRounded(context, item.getImageUrl(), helper.getView(R.id.adapter_commodity_image),5);
            helper.setText(R.id.adapter_commodity_title, item.getName());
            helper.setText(R.id.adapter_commodity_money,  item.getPrice());
            helper.setText(R.id.adapter_collection_discount,  item.getSave()+"元");
            helper.setText(R.id.adapter_collection_estimate_text,  item.getNormalCommission());
            ((TextView) helper.getView(R.id.adapter_commodity_yuanjia)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            helper.setText(R.id.adapter_commodity_yuanjia,item.getCost());
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
