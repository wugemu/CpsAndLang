package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.CategoryOne;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

public class LableSortAdapter extends BaseQuickAdapter<CategoryOne, BaseViewHolder> {

    private Context context;

    public LableSortAdapter(Context context, ArrayList<CategoryOne> list) {
        super(R.layout.adapter_lable_sort_item, list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryOne item) {
        try {
            helper.setText(R.id.lable_sort_text, item.getShopclassname());
            Utils.displayImage(context,item.getUrl(),helper.getView(R.id.lable_sort_iv));
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }


}
