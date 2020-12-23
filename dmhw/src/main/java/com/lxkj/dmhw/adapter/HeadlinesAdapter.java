package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

public class HeadlinesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context context;

    public HeadlinesAdapter(Context context, ArrayList<String> list) {
        super(R.layout.adapter_headlines, list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        try {
            helper.setText(R.id.adapter_headiness_text, item);
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
