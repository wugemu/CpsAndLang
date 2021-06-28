package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MarketSort;
import com.orhanobut.logger.Logger;


public class MarketTypeAdapter extends BaseQuickAdapter<MarketSort, BaseViewHolder> {

    private Activity activity;
    private int layoutSize;
    private MarketTypeAdapter.OnItemClickListener mListener;
    public void setOnItemClickListener(MarketTypeAdapter.OnItemClickListener li) {
        mListener = li;
    }
    public MarketTypeAdapter(Activity activity, int size) {
        super(R.layout.market_sort);
        this.activity = activity;
        this.layoutSize=size;
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketSort item) {
        try {
            LinearLayout limitMagicLayout =helper.getView(R.id.limit_magic_layout);
            TextView limit_magic_name=helper.getView(R.id.limit_magic_name);
            RecyclerView.LayoutParams linearParams =(RecyclerView.LayoutParams)limitMagicLayout.getLayoutParams();
            linearParams.width= layoutSize;
            limitMagicLayout.setLayoutParams(linearParams);
            helper.setText(R.id.limit_magic_name,item.getTitle());
            if (item.getIsSelect()==1){
                helper.getView(R.id.limit_magic_content_layout).setBackgroundResource(R.drawable.bg_rect_strock_black_20dp_white);
                limit_magic_name.setTextColor(Color.parseColor("#000000"));
            }else{
                helper.getView(R.id.limit_magic_content_layout).setBackgroundResource(R.drawable.maketing_sort_uncheck_bg);
                limit_magic_name.setTextColor(Color.parseColor("#999999"));
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
        void onItemClick(MarketSort data, int pos);
    }
}
