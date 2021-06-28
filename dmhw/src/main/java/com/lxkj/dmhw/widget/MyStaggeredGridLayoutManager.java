package com.lxkj.dmhw.widget;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

public class MyStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    public MyStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            //RecyclerView IndexOutOfBoundsException异常处理
            super.onLayoutChildren(recycler, state);
        }catch (Exception e){

        }
    }
}
