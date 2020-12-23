package com.lxkj.dmhw.defined;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 可嵌套在ScrollView中的WebView
 * Created by Android on 2018/8/6.
 */

public class ObserveWebView extends WebView {

    public ObserveWebView(Context context) {
        super(context);
    }

    public ObserveWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObserveWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }

}
