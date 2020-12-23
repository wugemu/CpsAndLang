package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MainBottomListItem;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;


public class CommodityAdapterPJW extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context context;

    public CommodityAdapterPJW(Context context) {
        super(R.layout.adapter_commodity_pjw);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        try {
            Utils.displayImage(context, item, helper.getView(R.id.adapter_commodity_image));
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
