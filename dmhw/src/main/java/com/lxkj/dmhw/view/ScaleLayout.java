package com.lxkj.dmhw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.lxkj.dmhw.R;

import java.lang.ref.WeakReference;

//RelativeLayout 根据长宽自适应
public class ScaleLayout extends RelativeLayout {
    float defaultWidthToHeight = 1.0f;//长宽默认1:1
    float widthToHeight;
 private WeakReference<Context> wr;
public ScaleLayout(Context context, AttributeSet attrs, int defStyle) {
super(context, attrs, defStyle);
 wr= new WeakReference<Context>(context);
 TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleLayout);
 widthToHeight = a.getFloat(R.styleable.ScaleLayout_widthToHeight, defaultWidthToHeight);
 a.recycle();
 }

 public ScaleLayout(Context context, AttributeSet attrs) {
 this(context, attrs,0);
 wr= new WeakReference<Context>(context);
 }


 public ScaleLayout(Context context) {
 this(context,null);
 wr= new WeakReference<Context>(context);
 }


 @Override
 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
 setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), (int) (getDefaultSize(0, widthMeasureSpec)*widthToHeight));
 // Children are just made to fill our space.
int childWidthSize = getMeasuredWidth();
int childHeightSize = getMeasuredHeight() ;
 widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
 heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);

 super.onMeasure(widthMeasureSpec,heightMeasureSpec);
 }
}

