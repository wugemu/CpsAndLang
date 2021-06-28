package com.lxkj.dmhw.defined;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 根据宽度设置高度的ImageView
 * Created by Administrator on 2017/12/21 0021.
 */

@SuppressLint("AppCompatCustomView")
public class ObserveImageView extends ImageView {
    public ObserveImageView(Context context) {
        super(context);
    }

    public ObserveImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ObserveImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, height);
    }
}
