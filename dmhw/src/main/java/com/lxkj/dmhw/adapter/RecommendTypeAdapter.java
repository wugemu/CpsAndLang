package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MarketSort;
import com.lxkj.dmhw.bean.RecommendSort;
import com.orhanobut.logger.Logger;


public class RecommendTypeAdapter extends BaseQuickAdapter<RecommendSort, BaseViewHolder> {

    private Activity activity;
    private int layoutSize;
    private RecommendTypeAdapter.OnItemClickListener mListener;
    public void setOnItemClickListener(RecommendTypeAdapter.OnItemClickListener li) {
        mListener = li;
    }
    public RecommendTypeAdapter(Activity activity, int size) {
        super(R.layout.recommend_sort);
        this.activity = activity;
        this.layoutSize=size;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendSort item) {
        try {
            helper.setText(R.id.limit_magic_name,item.getItemname());
            if (item.getIsSelect()==1){
                helper.setTextColor(R.id.limit_magic_name, Color.parseColor("#ffffff"));
                helper.getView(R.id.limit_magic_content_layout).setBackgroundResource(R.drawable.recommend_sort_check_bg);
            }else{
                helper.setTextColor(R.id.limit_magic_name, Color.parseColor("#666666"));
                helper.getView(R.id.limit_magic_content_layout).setBackgroundResource(0);
            }
            helper.getView(R.id.limit_magic_content_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(item,helper.getAdapterPosition());
                }
            });
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    public  interface OnItemClickListener {
        void onItemClick(RecommendSort data, int pos);
    }
}
