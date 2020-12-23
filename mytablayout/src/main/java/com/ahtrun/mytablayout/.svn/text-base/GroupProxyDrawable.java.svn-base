package com.ahtrun.mytablayout;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by 1 on 2018/3/21.
 */

public class GroupProxyDrawable extends Drawable {
    View view;
    Paint paint;

    float paddingLeft ;
    float paddingTop;
    int mSelectedIndicatorHeight;
    int indicatorWidth;
    public GroupProxyDrawable(View view) {
        mSelectedIndicatorHeight= (int) view.getContext().getResources().getDimension(R.dimen.dialog_sku_divider);
        indicatorWidth=(int) view.getContext().getResources().getDimension(R.dimen.indicator_width);
        this.view = view;
        paint = new Paint();
        paint.setColor(0xFFFF5A5F);
        paint.setAntiAlias(true);
        float density = view.getResources().getDisplayMetrics().density;
        //这两个留白可以根据需求更改
        paddingLeft = 0 * density;
        paddingTop =  5 * density;


    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //这里通过反射获取SlidingTabStrip的两个变量，源代码画的是下划线，我们现在画的是带圆角的矩形
        int mIndicatorLeft = getIntValue("mIndicatorLeft");
        int mIndicatorRight = getIntValue("mIndicatorRight");
        int margin= -(int)view.getContext().getResources().getDimension(R.dimen.group_tab_margin);
//        if(mIndicatorRight-mIndicatorLeft>indicatorWidth){
//            margin=(mIndicatorRight-mIndicatorLeft-indicatorWidth)/2;
//        }
        int height = view.getHeight();

        int radius = (int) view.getContext().getResources().getDimension(R.dimen.home_subject_line);
        if (mIndicatorLeft >= 0 && mIndicatorRight > mIndicatorLeft) {
            canvas.drawRoundRect(new RectF(mIndicatorLeft+margin, height - mSelectedIndicatorHeight+margin,
                    mIndicatorRight-margin, height+margin), radius, radius, paint);
        }
    }

    int getIntValue(String name) {
        try {
            Field f = view.getClass().getDeclaredField(name);
            f.setAccessible(true);
            Object obj = f.get(view);
            return (Integer) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
