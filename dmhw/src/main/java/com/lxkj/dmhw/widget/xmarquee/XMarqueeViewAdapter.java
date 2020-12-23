package com.lxkj.dmhw.widget.xmarquee;

import android.view.View;

import java.util.List;

/**
 * Created By lhp
 * on 2019/12/23
 */
public abstract class XMarqueeViewAdapter<T> {
    protected List<T> mDatas;
    private OnDataChangedListener mOnDataChangedListener;

    public XMarqueeViewAdapter(List<T> datas) {
        this.mDatas = datas;
        if (datas == null) {
            throw new RuntimeException("XMarqueeView datas is Null");
        }
    }

    public void setData(List<T> datas) {
        this.mDatas = datas;
        notifyDataChanged();
    }

    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public abstract View onCreateView(XMarqueeView parent);

    public abstract void onBindView(View parent, View view, int position);

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        mOnDataChangedListener = onDataChangedListener;
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onChanged();
        }
    }

    public interface OnDataChangedListener {
        void onChanged();
    }
}
