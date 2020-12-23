package com.lxkj.dmhw.adapter.self.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.self.cart.OrderContent;


/**
 * Created by lenovo on 2018/9/30.
 */

public class OrderGoodsSeparate implements OrderContent {
    @Override
    public int getLayout() {
        return R.layout.child_order_goods_separate;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public View getView(Context context, View convertView, LayoutInflater inflater) {
        if (convertView==null){
            convertView=inflater.inflate(getLayout(),null);
        }
        return convertView;
    }
}
