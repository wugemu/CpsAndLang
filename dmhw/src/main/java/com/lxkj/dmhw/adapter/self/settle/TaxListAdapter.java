package com.lxkj.dmhw.adapter.self.settle;

import android.app.Activity;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.TaxDetailBean;
import com.lxkj.dmhw.utils.BBCUtil;

import java.util.List;

public class TaxListAdapter extends BaseLangAdapter<TaxDetailBean> {
    public TaxListAdapter(Activity context, List<TaxDetailBean> data) {
        super(context, R.layout.listview_taxlist_item, data);
    }

    @Override
    public void convert(BaseLangViewHolder helper, int postion, TaxDetailBean item) {
        TextView tv_title_item=helper.getView(R.id.tv_title_item);
        TextView tv_value_item=helper.getView(R.id.tv_value_item);
        tv_title_item.setText(item.getTaxTitle());
        tv_value_item.setText("¥"+ BBCUtil.getDoubleFormat2(item.getTaxAmount()));
    }
}
