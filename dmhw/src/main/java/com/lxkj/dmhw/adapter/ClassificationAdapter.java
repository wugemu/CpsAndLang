package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.util.TypedValue;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ClassificationOne;
import com.lxkj.dmhw.bean.SuperSort;
import com.orhanobut.logger.Logger;

/**
 * 一级分类
 * Created by Android on 2018/8/13.
 */

public class ClassificationAdapter extends BaseQuickAdapter<SuperSort.dataTotal, BaseViewHolder> {

    private Context context;
    private int selectItem = 0;
    TextPaint tp;
    public ClassificationAdapter(Context context) {
        super(R.layout.adapter_classification);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SuperSort.dataTotal item) {
        try {
            helper.setText(R.id.adapter_classification_text, item.getName());
            TextView adapter_classification_text=helper.getView(R.id.adapter_classification_text);
            if (helper.getLayoutPosition() == selectItem) {
                item.setCheck(true);
                helper.setGone(R.id.adapter_classification_left, true);
                helper.setBackgroundColor(R.id.parent_layout,0xfff6f6f6);
                helper.setBackgroundRes(R.id.adapter_classification_layout, R.drawable.super_sort_item_bg);
                helper.setTextColor(R.id.adapter_classification_text, 0xFF000000);
                int dimen = context.getResources().getDimensionPixelSize(R.dimen.sp_13);
                adapter_classification_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,dimen);
                tp=adapter_classification_text.getPaint();
                tp.setFakeBoldText(true);
            }else {
                item.setCheck(false);
                helper.setGone(R.id.adapter_classification_left, false);
                helper.setTextColor(R.id.adapter_classification_text, 0xff666666);
                tp=adapter_classification_text.getPaint();
                tp.setFakeBoldText(false);
                int dimen = context.getResources().getDimensionPixelSize(R.dimen.sp_12);
                adapter_classification_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,dimen);
                if (selectItem-1>=0&&helper.getLayoutPosition()==selectItem-1) {//选中项上面相邻的块
                 helper.setBackgroundColor(R.id.parent_layout,0xffffffff);
                 helper.setBackgroundRes(R.id.adapter_classification_layout, R.drawable.super_sort_upitem_bg);
                }else if (selectItem+1<getItemCount()&&helper.getLayoutPosition()==selectItem+1) {//选中项下面的块
                 helper.setBackgroundColor(R.id.parent_layout,0xffffffff);
                 helper.setBackgroundRes(R.id.adapter_classification_layout, R.drawable.super_sort_downitem_bg);
                } else {
                 helper.setBackgroundRes(R.id.adapter_classification_layout, R.drawable.super_sort_item_bg1);
                }
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
}
