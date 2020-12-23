package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.BaseQuickAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

/**
 * 自动补全适配器
 * Created by Android on 2018/9/4.
 */

public class AutoCompleteAdapter extends BaseQuickAdapter<String> {


    public AutoCompleteAdapter(Context context) {
        super(context, R.layout.adapter_auto_complete);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.adapter_auto_complete_text, item);
    }
}
