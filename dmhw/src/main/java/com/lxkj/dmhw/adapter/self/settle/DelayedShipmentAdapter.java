package com.lxkj.dmhw.adapter.self.settle;

import android.app.Activity;

import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.GoodBean;

import java.util.List;

/**
 * Created By lhp
 * on 2019/10/25
 */
public class DelayedShipmentAdapter extends BaseLangAdapter<GoodBean> {

    public DelayedShipmentAdapter(Activity context,List<GoodBean> data) {
        super(context, R.layout.adapter_delayed_shipment, data);
    }

    @Override
    public void convert(BaseLangViewHolder baseLangViewHolder, int i, GoodBean goodBean) {
        baseLangViewHolder.setText(R.id.tv_product_name,goodBean.getGoodsName());
        baseLangViewHolder.setText(R.id.tv_tip,goodBean.getDelayedReminder());
    }
}
