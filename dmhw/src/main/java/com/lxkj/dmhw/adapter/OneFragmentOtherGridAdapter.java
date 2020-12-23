package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.OtherActivity;
import com.lxkj.dmhw.bean.CategoryTwo;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * 二级类目
 * Created by Zyhant on 2017/12/30.
 */

public class OneFragmentOtherGridAdapter extends BaseAdapter {

    private int number;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<CategoryTwo> list = new ArrayList<>();

    public OneFragmentOtherGridAdapter(Context context) {
        super();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_other_fragment, parent, false);
            holder = new Holder();
            holder.adapterOtherFragmentLayout = (LinearLayout) convertView.findViewById(R.id.adapter_other_fragment_layout);
            holder.adapterOtherFragmentImage = (ImageView) convertView.findViewById(R.id.adapter_other_fragment_image);
            holder.adapterOtherFragmentName = (TextView) convertView.findViewById(R.id.adapter_other_fragment_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        try {
            Utils.displayImage(context, list.get(position).getShopclasstwoimage(), holder.adapterOtherFragmentImage);
            holder.adapterOtherFragmentName.setText(list.get(position).getShopclassname());
            holder.adapterOtherFragmentLayout.setOnClickListener(v ->
                    context.startActivity(new Intent(context, OtherActivity.class)
                    .putExtra("CategoryOne", number)
                    .putExtra("CategoryTwo", Integer.parseInt(list.get(position).getShopclasstwo()))
                    .putExtra("title", list.get(position).getShopclassname())));
        } catch (Exception e) {
            Logger.e(e, "");
        }
        return convertView;
    }

    private class Holder {
        LinearLayout adapterOtherFragmentLayout;
        ImageView adapterOtherFragmentImage;
        TextView adapterOtherFragmentName;
    }

    /**
     * 写入数据
     *
     * @param list
     */
    public void setArrayList(ArrayList<CategoryTwo> list) {
        this.list = list;
    }

    /**
     * 写入一级类目
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
