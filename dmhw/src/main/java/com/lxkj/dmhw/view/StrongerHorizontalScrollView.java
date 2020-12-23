package com.lxkj.dmhw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.Utils;

public class StrongerHorizontalScrollView extends HorizontalScrollView {

    private ScrollViewListener scrollViewListener = null;
    private int lastXIntercepted, lastYIntercepted;

    public StrongerHorizontalScrollView(Context context) {
        super(context);
    }

    public StrongerHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public StrongerHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
    /**
     * 因为  HorizontalScrollView 自带的 OnScrollChangeListener 要6.0以上才能用，所以这里自定义
     */
    public interface ScrollViewListener {
        void onScrollChanged(StrongerHorizontalScrollView scrollView, int x, int y, int oldx, int oldy);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        final int deltaX = x - lastXIntercepted;
        final int deltaY = y - lastYIntercepted;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
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
