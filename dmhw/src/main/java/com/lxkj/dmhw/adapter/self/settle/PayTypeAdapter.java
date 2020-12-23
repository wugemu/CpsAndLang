package com.lxkj.dmhw.adapter.self.settle;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.PayTypeBean;

import java.util.List;

public class PayTypeAdapter extends BaseLangAdapter<PayTypeBean> {
    private int selectPosition;
    public PayTypeAdapter(Activity context, List<PayTypeBean> data) {
        super(context, R.layout.listview_paytype_item, data);
    }

    @Override
    public void convert(BaseLangViewHolder helper, final int postion, PayTypeBean item) {
        ImageView iv_paytype=helper.getView(R.id.iv_paytype);
        TextView tv_paytype=helper.getView(R.id.tv_paytype);
        RadioButton rb_wxcheck=helper.getView(R.id.rb_wxcheck);
        RelativeLayout rl_paytype_item=helper.getView(R.id.rl_paytype_item);
        if(item.getType()==8){
            iv_paytype.setImageResource(R.mipmap.wx);
        }else if(item.getType()==4){
            iv_paytype.setImageResource(R.mipmap.alipay);
        }
        if(selectPosition==postion){
            rb_wxcheck.setChecked(true);
        }else {
            rb_wxcheck.setChecked(false);
        }
        tv_paytype.setText(item.getTitle());
        rl_paytype_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition=postion;
                notifyDataSetChanged();
            }
        });
        rb_wxcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition=postion;
                notifyDataSetChanged();
            }
        });
    }

    public int getSelectPosition() {
        return selectPosition;
    }
}
