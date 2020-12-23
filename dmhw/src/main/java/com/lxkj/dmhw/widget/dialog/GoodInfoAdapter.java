package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.Info;

import java.util.List;

public class GoodInfoAdapter extends BaseLangAdapter<Info> {
    public GoodInfoAdapter(Activity context, List<Info> data) {
        super(context, R.layout.dialog_good_info, data);
    }

    @Override
    public void convert(BaseLangViewHolder helper, int postion, Info item) {
        TextView textView=helper.getView(R.id.tv_info_title);
        TextView tv_info_dec=helper.getView(R.id.tv_info_dec);
        textView.setText(item.getTitle());
        tv_info_dec.setText(item.getDes());
    }
}
