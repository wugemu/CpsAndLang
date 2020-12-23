package com.lxkj.dmhw.defined;

import android.content.Context;

import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuickAdapter<T> extends CommonAdapter<T> {

    public Context context;

    public BaseQuickAdapter(Context context, int layoutId) {
        super(context, layoutId, new ArrayList<>());
        this.context = context;
    }

    public void setData(List<T> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDatas;
    }
}
