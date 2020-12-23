package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.ObserveImageView;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * 缩略图适配器
 * Created by Administrator on 2017/12/20 0020.
 */

public class RewardAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> list = new ArrayList<>();
    private OnDeleteClickListener onDeleteClickListener;

    public RewardAdapter(Context context) {
        super();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
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
            convertView = layoutInflater.inflate(R.layout.adapter_reward, parent, false);
            holder = new Holder();
            holder.adapterRewardImage = (ObserveImageView) convertView.findViewById(R.id.adapter_reward_image);
            holder.adapterRewardDelete = (LinearLayout) convertView.findViewById(R.id.adapter_reward_delete);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        try {
            Utils.displayImage(context, "file://" + list.get(position), holder.adapterRewardImage);
            holder.adapterRewardDelete.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(position));
        } catch (Exception e) {
            Logger.e(e, "");
        }
        return convertView;
    }

    private class Holder {
        ObserveImageView adapterRewardImage;
        LinearLayout adapterRewardDelete;
    }

    /**
     * 写入数据
     *
     * @param list
     */
    public void setArrayList(ArrayList<String> list) {
        this.list = list;
    }

    /**
     * 删除事件
     */
    public interface OnDeleteClickListener {
        /**
         * 点击事件执行方法
         * @param position
         */
        void onDeleteClick(int position);
    }
}
