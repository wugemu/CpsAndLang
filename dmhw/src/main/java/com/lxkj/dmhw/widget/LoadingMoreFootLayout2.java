package com.lxkj.dmhw.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.lxkj.dmhw.R;

public class LoadingMoreFootLayout2 extends LoadingLayoutBase {
    private LinearLayout mInnerLayout;
    private FrameLayout fl_content;
    public LoadingMoreFootLayout2(Context context, boolean isHomeTheme) {
        super(context);
        View view= LayoutInflater.from(context).inflate(R.layout.foot_look_more2, this);
        mInnerLayout= (LinearLayout) view.findViewById(R.id.fl_inner);
    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {
    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {
//        this.mRefreshingLabel=refreshingLabel;
    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {
//        this.mReleaseLabel=releaseLabel;
    }

    @Override
    public int getContentSize() {
        return mInnerLayout.getWidth();
    }

    @Override
    public void pullToRefresh() {
        Log.i("","");
    }

    @Override
    public void releaseToRefresh() {
        Log.i("","");

    }

    @Override
    public void onPull(float scaleOfLayout) {
        Log.i("","");
    }

    @Override
    public void refreshing() {
        Log.i("","");
    }

    @Override
    public void reset() {

    }
}
