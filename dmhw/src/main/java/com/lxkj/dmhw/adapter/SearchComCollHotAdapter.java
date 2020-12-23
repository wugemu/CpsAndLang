package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Banner;

/**
 * 热搜
 * Created by Zyhant on 2018/1/8.
 */

public class SearchComCollHotAdapter extends BaseQuickAdapter<Banner, BaseViewHolder> {

    private Activity activity;
    private OnClickListener onClickListener;

    public SearchComCollHotAdapter(Activity activity) {
        super(R.layout.adapter_comcollsearch_hot);
        this.activity = activity;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, Banner item) {
        helper.setText(R.id.adapter_search_hot_text, item.getTitle());
        if (!item.getBgcolor().equals("")){
            helper.setGone(R.id.adapter_hot_image,true);
            helper.setTextColor(R.id.adapter_search_hot_text, Color.parseColor("#777777"));
        }else{
            helper.setGone(R.id.adapter_hot_image,false);
            helper.setTextColor(R.id.adapter_search_hot_text, Color.parseColor("#777777"));
        }
        helper.getView(R.id.adapter_search_hot_layout).setOnClickListener(v -> onClickListener.onHotClick(item));
    }

    /**
     * 点击事件
     */
    public interface OnClickListener {
        void onHotClick(Banner item);
    }
}
