package com.handmark.pulltorefresh.library.extras.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by 1 on 2017/5/22.
 */

public class MyRecylerView extends RecyclerView {

    private ViewGroup parent;
    private boolean disallowInterceptTouchEvent = false;

    public MyRecylerView(Context context) {
        super(context);
    }

    public MyRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.i("lhp", "MyGridView+dispatchTouchEvent:" + disallowIntercept);
        disallowInterceptTouchEvent=disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
    public void setNestParent(ViewGroup parent) {
        this.parent = parent;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("lhp","MyGridView+onInterceptTouchEvent:");
        try {
            disallowInterceptTouchEvent = false;
            return super.onInterceptTouchEvent(ev);
        }catch (IllegalArgumentException ex){

        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowInterceptTouchEvent);
        }
        Log.i("lhp", "MyGridView+onTouchEvent:");
        return super.onTouchEvent(ev);
    }
}
