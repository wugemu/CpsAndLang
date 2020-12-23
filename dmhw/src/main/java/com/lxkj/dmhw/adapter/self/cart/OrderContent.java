package com.lxkj.dmhw.adapter.self.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by 1 on 2017/4/18.
 *list多item布局基本类型
 */

public interface OrderContent {
     int getLayout();
     boolean isClickable();
     View getView(Context context, View convertView, LayoutInflater inflater);

}
