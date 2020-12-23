package com.lxkj.dmhw.adapter.self.cart;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.lxkj.dmhw.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2018/9/20.
 */

public class CartInvalidTop implements OrderContent {
    private Handler handler;
//    private CartResult cartResult;
    private int total;


    public CartInvalidTop( Handler handler) {
        this.handler = handler;
//        this.cartResult = cartResult;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int getLayout() {
        return R.layout.child_cart_invalid_top;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public View getView(Context context, View convertView, LayoutInflater inflater) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(getLayout(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvInvalid.setText("失效商品(" + total + ")");
        holder.tvHeadtitlefree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空失效商品
                handler.sendEmptyMessage(2);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_invalid)
        TextView tvInvalid;
        @BindView(R.id.tv_headtitlefree)
        TextView tvHeadtitlefree;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
