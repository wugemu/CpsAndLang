package com.lxkj.dmhw.defined;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 重写ScrollView，监听滑动位置
 * Created by Administrator on 2017/12/8 0008.
 */

public class ObserveScrollView extends ScrollView {

    private OnScrollListener onScroll;

    public ObserveScrollView(Context context) {
        super(context);
    }

    public ObserveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObserveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public interface OnScrollListener {
        void onScroll(int scrollY);
    }

    public void setOnScrollListener(OnScrollListener onScroll) {
        this.onScroll = onScroll;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(onScroll != null){
            onScroll.onScroll(y);
        }
    }
}
