package com.lxkj.dmhw.view.inspectroom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Scroller;

import com.lxkj.dmhw.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * gwj
 * 自定义控件  实现自动消失 控件 回收
 */

public class MessagQuenView extends ViewGroup {
    private Context context;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int viewHeight = 0;
    private int childViewHeight = 0;
    private float lastDownY;
    private String TAG = "tag";

    public MessagQuenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MessagQuenView(Context context) {
        super(context);
    }

    /*
     * 回收线程， 每隔一段时间去检查过期的View 触发回收事件
     */
    private ScheduledExecutorService mservice = Executors.newSingleThreadScheduledExecutor();

    private void setSchedule() {

        if (mservice.isShutdown()) {
            mservice = Executors.newSingleThreadScheduledExecutor();
        }
        mservice.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                schedualRemove();
            }
        }, 0, 1200, TimeUnit.MILLISECONDS);
    }

    public void shutDownSchedulSeervice() {
        mservice.shutdownNow();
    }

    private void init() {
        setSchedule();
        //初始化滑动的类
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        int measureTempHeight = 0;
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            measureTempHeight = measureTempHeight + getChildAt(i).getMeasuredHeight();
        }
        childViewHeight = measureTempHeight;
        setMeasuredDimension(measureWidthSize(widthMeasureSpec), measureSize(heightMeasureSpec));
        viewHeight = getHeight();
        Log.e(TAG, "childViewHeight=" + childViewHeight);
        Log.e(TAG, "viewHeight == " + viewHeight);
    }

    private int measureWidthSize(int widthMeasureSpec) {
        int result = 0; // 结果
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST: // 子容器可以是声明大小内的任意大小
                result = specSize;
                break;
            case MeasureSpec.EXACTLY: // 父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间.
                // 比如EditTextView中的DrawLeft
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED: // 父容器对于子容器没有任何限制,子容器想要多大就多大.
                // 所以完全取决于子view的大小
                result = 1500;
                break;
            default:
                break;
        }
        return result * 2 / 3;
    }

    private int measureSize(int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int result = 0; // 结果
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST: // 子容器可以是声明大小内的任意大小
                result = specSize;
                break;
            case MeasureSpec.EXACTLY: // 父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间.
                // 比如EditTextView中的DrawLeft
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED: // 父容器对于子容器没有任何限制,子容器想要多大就多大.
                // 所以完全取决于子view的大小
                result = 1500;
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        /*
         * 设置 左右的 padding
		 */
        l = l + getPaddingLeft();
        r = r - getPaddingRight();
        t = t + getPaddingTop();
        b = b - getPaddingBottom();

        Stack<View> drawChild = new Stack<View>();
        drawChild.clear();
        for (Iterator<View> itrator = childQueue.iterator(); itrator.hasNext(); ) {
            View child = itrator.next();
            drawChild.add(child);
        }
        // 设置 绘制最顶层的高度
        int dHeight = b;
        while (drawChild.size() >= 1) {
            View child = drawChild.pop();
            child.layout(l, dHeight - child.getMeasuredHeight(), r, dHeight);
            dHeight = dHeight - child.getHeight();
        }
    }

    /*
     * 	所有子View的List容器
     */
    private List<View> childQueue = new ArrayList<View>();

    /*
     * 通知View 移除控件操作
     */
    private final Handler handlerAnim = new Handler() {
        public void handleMessage(Message msg) {
            removeView((View) msg.obj);
            childQueue.remove((View) msg.obj);

        }
    };

    /*
     * 通知View 移除控件操作
     */
    private final Handler handlerCallRemove = new Handler() {
        public void handleMessage(Message msg) {

            final View child = (View) msg.obj;
            child.setVisibility(View.VISIBLE);
            Animation anim;
            if (msg.arg2%2==1){
                anim = AnimationUtils.loadAnimation(context, R.anim.live_message_out);
            }else{
                anim = AnimationUtils.loadAnimation(context, R.anim.live_message_out_sec);
            }
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Message msg = handlerAnim.obtainMessage();
                    msg.obj = child;
                    handlerAnim.sendMessage(msg);
                }
            });
            child.startAnimation(anim);

        }

    };

    public void schedualRemove() {
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child != null) {
                ItemTag tag = (ItemTag) child.getTag();
                if (!tag.isMove) {
                    int delt = (int) ((System.currentTimeMillis() - tag.time) / 1000);
                    if (delt > 2) {
                        // 重新设置 tag 标签
                        tag.isMove = true;
                        child.setTag(tag);

                        Message msg = handlerCallRemove.obtainMessage();
                        msg.obj = child;
                        msg.arg2=tag.num;
                        handlerCallRemove.sendMessage(msg);
                    }
                } else {
                    // tag 设置 wei true

                }

            }
        }
    }

    // 添加View 控件
    public void addNewView(View childv,int num) {
        ItemTag tag = new ItemTag();
        tag.time = System.currentTimeMillis();
        tag.num=num;
        childv.setTag(tag);
        childQueue.add(childv);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.live_im_msgv_scal_in);
        childv.startAnimation(anim);
        this.addView(childv);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastDownY = event.getY();
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = event.getY();
                float dy;
                dy = lastDownY - currentY;
                lastDownY = event.getY();
                mVelocityTracker.addMovement(event);
                //dy>0向上滑动  超过水平线  getScrollY  <0  低于最低水平
//                if (dy >= 0 && getScrollY() > 0) {
//                    Log.e(TAG, "dy >===0");
//                    return true;
//                }
//                //dy<0向下滑动  低于水平线 getScrollY >0  超过最高水平
//                if (dy <= 0 && getScrollY() < viewHeight - childViewHeight) {
//                    Log.e(TAG, "dy <====0");
//                    return true;
//                }

                if (childViewHeight > viewHeight) {
                    scrollBy(0, (int) dy);
                }
                Log.e(TAG, "scrollY =" + getScrollY());
                break;
            case MotionEvent.ACTION_UP:
//                if (childViewHeight > viewHeight) {
//                    if (getScrollY() > 0) {
//                        mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
//                    } else if (getScrollY() < viewHeight - childViewHeight) {
//                        int temp = (viewHeight - childViewHeight);
//                        mScroller.startScroll(0, getScrollY(), 0, temp - getScrollY());
//                    }
//                }
                if (childViewHeight > viewHeight) {
                    mVelocityTracker.computeCurrentVelocity(1000);
                    float velocityY = mVelocityTracker.getYVelocity();
                    mScroller.fling(0, getScrollY(), 0, (int) -velocityY, 0, 0, viewHeight - childViewHeight, 0);
                    postInvalidate();
                } else {
//                    mScroller.startScroll();
                    mScroller.startScroll(0, getScrollY(), 0, -getScrollY());

                }

                break;
        }
        return false;
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {//是否已经滚动完成
            scrollTo(0, mScroller.getCurrY());//获取当前值，startScroll（）初始化后，调用就能获取区间值
            postInvalidate();
        }
    }

    /*
     * View的tag标记
     */
    class ItemTag {
        public long time;
        public boolean isMove = false;
        public int num;//属于第几个出来
    }



}
