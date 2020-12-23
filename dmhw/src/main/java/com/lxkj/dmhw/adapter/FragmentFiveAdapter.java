package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.lxkj.dmhw.defined.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MyService;
import com.lxkj.dmhw.utils.Utils;
import com.zhy.adapter.abslistview.ViewHolder;

/**
 * 我的服务适配器
 * Created by Android on 2018/8/16.
 */

public class FragmentFiveAdapter extends BaseQuickAdapter<MyService> {

    public FragmentFiveAdapter(Context context) {
        super(context, R.layout.adapter_fragment_five);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MyService item, int position) {
        Utils.displayImage(context, item.getFuncico(), viewHolder.getView(R.id.adapter_fragment_five_image));
        viewHolder.setText(R.id.adapter_fragment_five_text, item.getFuncname());
    }
}
