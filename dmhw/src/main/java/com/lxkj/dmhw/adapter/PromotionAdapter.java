package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.lxkj.dmhw.defined.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Promotion;
import com.lxkj.dmhw.utils.Utils;
import com.zhy.adapter.abslistview.ViewHolder;

public class PromotionAdapter extends BaseQuickAdapter<Promotion> {

    private OnPromotionClickListener onPromotionClickListener;

    public PromotionAdapter(Context context) {
        super(context, R.layout.adapter_promotion);
    }

    public void setOnPromotionClickListener(OnPromotionClickListener onPromotionClickListener) {
        this.onPromotionClickListener = onPromotionClickListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Promotion item, int position) {
        Utils.displayImage(context, item.getFuncico(), viewHolder.getView(R.id.adapter_promotion_image));
        viewHolder.setText(R.id.adapter_promotion_text, item.getFuncname());
        viewHolder.getView(R.id.adapter_promotion_layout).setOnClickListener(v -> onPromotionClickListener.onPromotion(item));
    }

    public interface OnPromotionClickListener {
        /**
         * 点击事件执行方法
         * @param item
         */
        void onPromotion(Promotion item);
    }
}
