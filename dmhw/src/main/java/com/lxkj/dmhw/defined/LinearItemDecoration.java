package com.lxkj.dmhw.defined;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView列表间距设置
 * Created by Zyhant on 2018/1/9.
 */

public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private int left, top, right, bottom;

    public LinearItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = left;
        outRect.right = right;
        outRect.top = top;
        outRect.bottom = bottom;
    }

}
