package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ClassificationTwo;
import com.lxkj.dmhw.bean.SuperSort;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * 二级分类
 * Created by Android on 2018/8/15.
 */

public class ClassificationGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SuperSort.dataTotal.Datatwo> list = new ArrayList<>();

    public ClassificationGridAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_classification_grid, parent, false);
            holder = new Holder();
            holder.adapterClassificationGridImage = (ImageView) convertView.findViewById(R.id.adapter_classification_grid_image);
            holder.adapterClassificationGridText = (TextView) convertView.findViewById(R.id.adapter_classification_grid_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        try {
            Utils.displayImageRoundedAll(context, list.get(position).getLogo(), holder.adapterClassificationGridImage,R.dimen.dp_24);
            holder.adapterClassificationGridText.setText(list.get(position).getName());
        } catch (Exception e) {
            Logger.e(e, "");
        }
        return convertView;
    }

    private class Holder {
        ImageView adapterClassificationGridImage;
        TextView adapterClassificationGridText;
    }

    public void setArrayList(ArrayList<SuperSort.dataTotal.Datatwo> list) {
        this.list = list;
    }
}
