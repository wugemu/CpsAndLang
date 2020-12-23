package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ComCollegeData;
import com.lxkj.dmhw.bean.PersonalAppIcon;
import com.lxkj.dmhw.defined.BaseQuickAdapter;
import com.lxkj.dmhw.utils.Utils;
import com.zhy.adapter.abslistview.ViewHolder;
/**
 *商学院 轮播图下面部分
 **/
public class ComCollPartOneAdapter extends BaseQuickAdapter<ComCollegeData.PartOne> {

    private OnPersonalAppIconClickListener onPersonalAppIconClickListener;

    public ComCollPartOneAdapter(Context context) {
        super(context, R.layout.adapter_comcollpartone);
    }

    public void setOnPersonalAppIconClickListener(OnPersonalAppIconClickListener onPersonalAppIconClickListener) {
        this.onPersonalAppIconClickListener = onPersonalAppIconClickListener;
    }
    @Override
    protected void convert(ViewHolder viewHolder, ComCollegeData.PartOne item, int position) {
        Utils.displayImage(context, item.getImgurl(), viewHolder.getView(R.id.adapter_partone_image));
        TextView adapter_partone_text=viewHolder.getView(R.id.adapter_partone_text);
        viewHolder.setText(R.id.adapter_partone_text, item.getTitle());
        TextPaint tp= adapter_partone_text.getPaint();
        tp.setFakeBoldText(true);
        viewHolder.setText(R.id.adapter_partone_subtext, item.getRemark());
        viewHolder.getView(R.id.adapter_promotion_layout).setOnClickListener(v -> onPersonalAppIconClickListener.onPersonalAppIconClick(item));
    }

    public interface OnPersonalAppIconClickListener {
        /**
         * 点击事件执行方法
         * @param item
         */
        void onPersonalAppIconClick(ComCollegeData.PartOne item);
    }
}
