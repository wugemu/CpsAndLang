package com.lxkj.dmhw.defined;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 加载进度条
 * Created by Zyhant on 2016/8/2.
 */
public class ProgressView extends View {

    public static final int STATE_BUTTON = 0;
    public static final int STATE_PROGRESS = 1;

    public int mMaxProgressl;

    private int mFColor = 0x8800afe9;
    private int mBColor = 0x88878787;


    private Paint mPaint;
    private int mProgress;

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0xff000000);
    }

    public void setMaxPregress(int max){
        mMaxProgressl = max;
        invalidate();
    }

    public void setProgress(int progress){
        mProgress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(mBColor);
        super.onDraw(canvas);
        if(mMaxProgressl != 0){
            mPaint.setColor(mFColor);
            float p = ((getRight()-getLeft())*mProgress)/mMaxProgressl;
            canvas.drawRect(0, 0, p, getBottom()-getTop(), mPaint);
        }
    }
}
