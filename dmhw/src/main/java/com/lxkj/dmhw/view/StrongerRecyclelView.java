package com.lxkj.dmhw.view;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

//左右滑动和上下滑动冲突
public class StrongerRecyclelView extends RecyclerView {

    private int lastXIntercepted, lastYIntercepted;

    public StrongerRecyclelView(Context context) {
        super(context);
    }

    public StrongerRecyclelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public StrongerRecyclelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        final int deltaX = x - lastXIntercepted;
        final int deltaY = y - lastYIntercepted;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (Math.abs(deltaX)> Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(deltaX)> Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastXIntercepted = x;
        lastYIntercepted = y;
        return super.dispatchTouchEvent(ev);
    }

}
