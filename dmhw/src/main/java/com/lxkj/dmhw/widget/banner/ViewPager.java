package com.lxkj.dmhw.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.test.andlang.util.LogUtil;

public class ViewPager extends LoopViewPager {

	private boolean scrollable = true;

	public ViewPager(Context context) {
		super(context);
	}

	public ViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int height = 0;
//		for (int i = 0; i < getChildCount(); i++) {
//			View child = getChildAt(i);
//			child.measure(widthMeasureSpec,
//					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//			int h = child.getMeasuredHeight();
//			if (h > height)
//				height = h;
//		}
//
//		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
//				View.MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return scrollable && super.onInterceptTouchEvent(ev);
		}catch (IllegalArgumentException ex){

		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return scrollable && super.onTouchEvent(ev);
	}
}
