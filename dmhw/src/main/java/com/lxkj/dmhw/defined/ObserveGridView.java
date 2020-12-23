package com.lxkj.dmhw.defined;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义的“九宫格”——用在显示帖子详情的图片集合
 * 解决的问题：GridView显示不全，只显示了一行的图片
 * Created by Administrator on 2017/12/13 0013.
 */

public class ObserveGridView extends GridView {

    public ObserveGridView(Context context) {
        super(context);
    }

    public ObserveGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObserveGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
