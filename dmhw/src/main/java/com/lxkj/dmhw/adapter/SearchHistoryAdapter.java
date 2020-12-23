package com.lxkj.dmhw.adapter;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;

/**
 * 搜索历史
 * Created by Android on 2018/9/13.
 */

public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Activity activity;
    private OnClickListener onClickListener;

    public SearchHistoryAdapter(Activity activity) {
        super(R.layout.adapter_search_history);
        this.activity = activity;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.adapter_search_history_text, item);
        helper.getView(R.id.adapter_search_history_layout).setOnClickListener(v -> onClickListener.onHistoryClick(item));
    }

    /**
     * 点击事件
     */
    public interface OnClickListener {
        void onHistoryClick(String content);
    }

}
