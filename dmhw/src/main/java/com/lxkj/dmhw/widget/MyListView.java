package com.lxkj.dmhw.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by 1 on 2015/12/23.
 */
public class MyListView extends ListView {
    private ViewGroup parent;
    private boolean disallowInterceptTouchEvent = false;

    private int downX;

    private int downY;

    private int mTouchSlop;

    public MyListView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.i("lhp", "MyGridView+dispatchTouchEvent:" + disallowIntercept);
        disallowInterceptTouchEvent = disallowIntercept;
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
    public boolean onTouchEvent(MotionEvent ev) {
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowInterceptTouchEvent);
        }
        Log.i("lhp", "MyGridView+onTouchEvent:");
        return super.onTouchEvent(ev);
    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent e) {
        try {
            int action = e.getAction();

            switch (action) {

                case MotionEvent.ACTION_DOWN:

                    downX = (int) e.getRawX();

                    downY = (int) e.getRawY();

                    break;

                case MotionEvent.ACTION_MOVE:

                    int moveY = (int) e.getRawY();

                    if (Math.abs(moveY - downY) > mTouchSlop) {
                        //需要调用下
                        super.onInterceptTouchEvent(e);
                        return true;

                    }

            }
            disallowInterceptTouchEvent = false;
            return super.onInterceptTouchEvent(e);
        }catch (IllegalArgumentException ex){

        }
        return false;

    }


}
