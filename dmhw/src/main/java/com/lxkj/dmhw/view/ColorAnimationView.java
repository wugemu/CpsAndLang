package com.lxkj.dmhw.view;
import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.fragment.OneFragment290;

public class ColorAnimationView extends View implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    private static final int ONE = 0xE5000000;
    private static final int TWO = 0xE5ffffff;
    private static final int THREE = 0xE5FFFF00;
    private static final int FOUR = 0xE500FF00;
    private static final int FIVE = 0xff00FFFF;
    private static final int SIX = 0xff0000FF;
    private static final int SEVEN= 0xff8B00FF;
    private static final int DURATION =3000;
    ValueAnimator colorAnim = null;
    int [] colors={};
    private PageChangeListener mPageChangeListener;
    private int viewPagerHeight;
    ViewPager.OnPageChangeListener onPageChangeListener;
    int currentPosColor=0xFFFC1365;
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }



    /**
     * 这是你唯一需要关心的方法
     * @param mViewPager  你必须在设置 Viewpager 的 Adapter 这后，才能调用这个方法。
     * @param count   ,viewpager孩子的数量
     * @param colors int... colors ，你需要设置的颜色变化值~~ 如何你传空，那么触发默认设置的颜色动画
     * */
    /**
     * This is the only method you need care about.
     * @param mViewPager  ,you need set the adpater before you call this.
     * @param count   ,this param set the count of the viewpaper's child
     * @param colors ,this param set the change color use (int... colors),
     *               so,you could set any length if you want.And by default.
     *               if you set nothing , don't worry i have already creat
     *               a default good change color!
     * */
    public void setmViewPager(ViewPager mViewPager, int count, int... colors) {
//		this.mViewPager = mViewPager;
        if (mViewPager.getAdapter() == null) {
            throw new IllegalStateException(
                    "ViewPager does not have adapter instance.");
        }
        mPageChangeListener.setViewPagerChildCount(count);

        mViewPager.setOnPageChangeListener(mPageChangeListener);
        if (colors!=null||colors.length >0) {
            this.colors=colors;
        }

    }

    public ColorAnimationView(Context context) {
        this(context, null, 0);

    }

    public ColorAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPageChangeListener = new PageChangeListener();

    }


    @Override public void onAnimationStart(Animator animation) {

    }

    @Override public void onAnimationEnd(Animator animation) {
    }

    @Override public void onAnimationCancel(Animator animation) {

    }

    @Override public void onAnimationRepeat(Animator animation) {

    }

    @Override public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }

    private class PageChangeListener
            implements ViewPager.OnPageChangeListener {

        private int viewPagerChildCount;

        public void setViewPagerChildCount(int viewPagerChildCount) {
            this.viewPagerChildCount = viewPagerChildCount;
        }

        public int getViewPagerChildCount() {
            return viewPagerChildCount;
        }

        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (getViewPagerChildCount()>=2){
                currentPosColor=colors[(position%getViewPagerChildCount())];
            }
            if (getViewPagerChildCount()>=2&&!Variable.noChangeBg&&OneFragment290.isTopBg) {
                int realPos=position%getViewPagerChildCount();
                ArgbEvaluator evaluator = new ArgbEvaluator(); // ARGB求值器
                int evaluate = 0x00FFFFFF; // 初始默认颜色（透明白）
                if (realPos==getViewPagerChildCount()-1){
                 evaluate=(Integer) evaluator.evaluate(positionOffset, colors[realPos], colors[0]);
                }else{
                 evaluate=(Integer) evaluator.evaluate(positionOffset, colors[realPos], colors[realPos+1]);
                }
                ColorAnimationView.this.setBackgroundColor(evaluate); // 为ViewPager的父容器设置背景色

            }
            // call the method by default
            if (onPageChangeListener!=null){
                onPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

        }

        @Override public void onPageSelected(int position) {
            if (onPageChangeListener!=null) {
                onPageChangeListener.onPageSelected(position);
            }
        }

        @Override public void onPageScrollStateChanged(int state) {
            if (onPageChangeListener!=null) {
                onPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    }


    public  void setChangeBackgroundColor(){
        ColorAnimationView.this.setBackgroundColor(currentPosColor);
    }

}