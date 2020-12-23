package com.lxkj.dmhw.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lxkj.dmhw.R;

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private int padding;
    private int leftrightpadding;
    private int topbottompadding;

    public MyItemDecoration(Context context) {
        this.context=context;
        padding=(int) context.getResources().getDimension(R.dimen.dp_10);
        leftrightpadding=padding;
        topbottompadding=padding;
    }

    public MyItemDecoration(Context context,int leftrightpadding,int topbottompadding) {
        this.context=context;
        this.leftrightpadding=leftrightpadding;
        this.topbottompadding=topbottompadding;
    }

    /**
     * @param outRect 边界
     * @param view    recyclerView ItemView
     * @param parent  recyclerView
     * @param state   recycler 内部数据管理
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) == 0||(parent.getLayoutManager() instanceof GridLayoutManager && parent.getChildLayoutPosition(view) == 1)){
            outRect.set(leftrightpadding, topbottompadding, leftrightpadding, topbottompadding);
        }else {
            outRect.set(leftrightpadding, 0, leftrightpadding, topbottompadding);
        }
    }
}
