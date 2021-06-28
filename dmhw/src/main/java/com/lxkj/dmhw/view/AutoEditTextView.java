package com.lxkj.dmhw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.Utils;

/**
 * Created by ytt on 2018/7/19.
 * 定义一个左侧可以填写文字，右侧可以放置图片，点击右侧图片根据type实现功能
 * 还有下划线，下划线颜色根据获取焦点和未获取焦点有两种状态
 * 右侧图片功能
 * 1.删除当前输入文字，只有当前edittext有内容时才会显示该图片
 * 2.密码的显示和隐藏
 */
public class AutoEditTextView extends androidx.appcompat.widget.AppCompatEditText {
    //TODO 左侧的文字内容，没有则不设置
    private String leftText;
    //TODO 左侧文字的颜色
    private int leftTextColor;
    //TODO 左侧文字的大小
    private float leftTextSize;
    //TODO 左侧文字距离光标的距离
    private int leftTextPadding;
    //TODO 右侧图标点击的功能，0删除当前内容，1隐藏和显示密码
    private int clickType;
    //TODO 是否要显示底部的线
    private boolean isShowDownLine;
    //TODO 底部线显示的默认颜色
    private int downLineDefaultColor;
    //TODO 底部线现实的选中颜色
    private int downLineFocusColor;
    //绘制左侧文字的画笔
    private Paint mTextPaint;
    //绘制下方线的笔
    private Paint mLinePaint;
    private boolean isShowPass;

    public AutoEditTextView(Context context) {
        this(context, null);
    }

    public AutoEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public AutoEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.AutoEditTextView, defStyleAttr, 0);
        leftText = typeArray.getString(R.styleable.AutoEditTextView_leftText);
        leftTextColor = typeArray.getColor(R.styleable.AutoEditTextView_leftTextColor, getColor(R.color.color_666666));
        leftTextSize = typeArray.getFloat(R.styleable.AutoEditTextView_leftTextSize, sp2px(16f));
        clickType = typeArray.getInt(R.styleable.AutoEditTextView_rightButtonClickType, 0);
        isShowDownLine = typeArray.getBoolean(R.styleable.AutoEditTextView_isShowDownLine, true);
        leftTextPadding = typeArray.getInt(R.styleable.AutoEditTextView_leftTextPadding, dip2px(18));
        downLineDefaultColor = typeArray.getColor(R.styleable.AutoEditTextView_downLineDefaultColor, getColor(R.color.mainColor));
        downLineFocusColor = typeArray.getColor(R.styleable.AutoEditTextView_downLineFocusColor, getColor(R.color.mainColor));
        typeArray.recycle();
//        setBackground(null);
        initPaint();
    }


    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(leftTextColor);
        mTextPaint.setTextSize(leftTextSize);
        mTextPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setColor(downLineDefaultColor);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(dip2px(1));

        //TODO 如果需要设置左侧的问题，需要重新计算padding
        if (!TextUtils.isEmpty(leftText)) {
            int paddingLeft = (int) mTextPaint.measureText(leftText) + getPaddingLeft() + leftTextPadding;
            //设置距离光标距离左侧的距离
            setPadding(paddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (isShowDownLine) {
            mLinePaint.setColor(focused ? downLineFocusColor : downLineDefaultColor);
            invalidate();
        }
    }

    /**
     * getScrollX()这个方法是当editText只显示一行时，如果起始点设置成0会被挤出屏幕，
     * 看了一下TextView和View的源码才分析出来的
     * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //TODO 绘制左侧的文字,并居中显示
        if (!TextUtils.isEmpty(leftText)) {
            canvas.drawText(leftText, Utils.dipToPixel(R.dimen.dp_10), (getMeasuredHeight() -leftTextSize) / 2 + leftTextSize, mTextPaint);
        }
        //绘制底部的线
        if (isShowDownLine) {
            canvas.drawLine(getScrollX(), this.getHeight() - dip2px(1), getScrollX() + this.getWidth(), this.getHeight() - dip2px(1), mLinePaint);
        }
    }

    /* @说明：isInnerWidth, isInnerHeight为ture，触摸点在删除图标之内，则视为点击了删除图标
     * event.getX() 获取相对应自身左上角的X坐标
     * event.getY() 获取相对应自身左上角的Y坐标
     * getWidth() 获取控件的宽度
     * getHeight() 获取控件的高度
     * getTotalPaddingRight() 获取删除图标左边缘到控件右边缘的距离
     * getPaddingRight() 获取删除图标右边缘到控件右边缘的距离
     * isInnerWidth:
     *  getWidth() - getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离
     *  getWidth() - getPaddingRight() 计算删除图标右边缘到控件左边缘的距离
     * isInnerHeight:
     *  distance 删除图标顶部边缘到控件顶部边缘的距离
     *  distance + height 删除图标底部边缘到控件顶部边缘的距离
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = getCompoundDrawables()[2].getBounds();
                int height = rect.height();
                int distance = (getHeight() - height) / 2;
                boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight()) && x < (getWidth() - getPaddingRight());
                boolean isInnerHeight = y > distance && y < (distance + height);
                if (isInnerWidth && isInnerHeight) {
                    dealRightButtonClickType();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    //TODO 处理右侧图标点击事件
    private void dealRightButtonClickType() {
        if (clickType == 0) {//删除当前内容
            setText("");
        } else if (clickType == 1) {//隐藏和显示密码
            isShowPass = !isShowPass;
            setTransformationMethod(isShowPass ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                    isShowPass ? getDrawable(0) : getDrawable(0), getCompoundDrawables()[3]);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (!TextUtils.isEmpty(text)) {
            setSelection(text.toString().length() - 1);
        }
    }

    public Drawable getDrawable(int id) {
        Drawable drawable = getContext().getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    //dip--->px   1dp = 1px   1dp = 2px
    public int dip2px(double dip) {
        //dp和px的转换关系比例值
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }
}
