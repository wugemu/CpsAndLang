package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.lxkj.dmhw.defined.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.BrandList;
import com.zhy.adapter.abslistview.ViewHolder;

public class BrandAdapter extends BaseQuickAdapter<BrandList> {

    public BrandAdapter(Context context) {
        super(context, R.layout.adapter_brand);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BrandList item, int position) {
        viewHolder.setText(R.id.adapter_brand_text, item.getCn());
        viewHolder.setBackgroundRes(R.id.adapter_brand_layout, item.isCheck() ? R.drawable.adapter_brand_layout_select : R.drawable.adapter_brand_layout_default);
        viewHolder.setTextColor(R.id.adapter_brand_text, item.isCheck() ? context.getResources().getColor(R.color.mainColor) : 0xff333333);
    }

    /**
     * 选中
     * @param position
     */
    public void setSelect(int position) {
        mDatas.get(position).setCheck(!mDatas.get(position).isCheck());
        notifyDataSetChanged();
    }
}
