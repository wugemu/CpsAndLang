package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.JDBanner;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;


/**
 * 京东专区数据
 */

public class JDNewDataAdapterThree340 extends BaseQuickAdapter<JDBanner,BaseViewHolder> {

    private Context context;
    private JDNewDataAdapterThree340.OnItemClickListener mListener;
    public void setOnItemClickListener(JDNewDataAdapterThree340.OnItemClickListener li) {
        mListener = li;
    }

    public JDNewDataAdapterThree340(Context context) {
        super(R.layout.jd_newdata_three_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, JDBanner item) {
        try {
            Utils.displayImageRoundedAll(context, item.getImageUrl(), viewHolder.getView(R.id.image_iv), 5);
            viewHolder.setText(R.id.title,item.getName());
            TextView textView=viewHolder.getView(R.id.title);
            TextPaint tp= textView.getPaint();
            tp.setFakeBoldText(true);
            viewHolder.setText(R.id.subtitle,item.getSubName());
            if(mListener != null) {
                viewHolder.getView(R.id.total_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(viewHolder.getAdapterPosition(), item);
                    }
                });
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(int position, JDBanner data);
    }
}
