package com.lxkj.dmhw.adapter.self.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.self.cart.OrderContent;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.utils.BBCUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2018/10/8.
 */

public class OrderDetialTop implements OrderContent {
    private OrderBean orderBean;
    public OrderDetialTop(OrderBean orderBean) {
        this.orderBean=orderBean;
    }

    @Override
    public int getLayout() {
        return R.layout.adapter_order_detial_top;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public View getView(Context context, View convertView, LayoutInflater inflater) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getLayout(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(!BBCUtil.isEmpty(orderBean.getWarehouseName())) {
            holder.tvPostName.setText(orderBean.getWarehouseName());
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_post_name)
        TextView tvPostName;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
