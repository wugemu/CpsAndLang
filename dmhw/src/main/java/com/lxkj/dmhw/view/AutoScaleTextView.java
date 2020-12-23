package com.lxkj.dmhw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.utils.Utils;

public class AutoScaleTextView extends AppCompatTextView {
    private static final String TAG = "AutoScaleTextview";
    private float preferredTextSize;
    private float minTextSize;
    private Paint textPaint;

    public AutoScaleTextView(Context context) {
        super(context, null);
    }

    public AutoScaleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.autoScaleTextViewStyle);
    }

    public AutoScaleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.textPaint = new Paint();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoScaleTextView, defStyleAttr, 0);
        this.minTextSize = a.getDimension(R.styleable.AutoScaleTextView_minTextSize, 10f);
        a.recycle();
        this.preferredTextSize = this.getTextSize();

    }

    /**
     * 设置最小的size
     *
     * @param minTextSize
     */
    public void setMinTextSize(float minTextSize) {
        this.minTextSize = minTextSize;
    }

    /**
     * 根据填充内容调整textview
     *
     * @param text
     * @param textWidth
     */
    private void refitText(String text, int textWidth) {
        if (textWidth <= 0 || text == null || text.length() == 0) {
            return;
        }
        int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();

        if(targetWidth<(Variable.ScreenWidth- Utils.dipToPixel(R.dimen.dp_80))/3){
            this.textPaint.setTextSize(this.preferredTextSize);
            return;
        }

        final float threshold = 0.5f;

        this.textPaint.set(this.getPaint());

        this.textPaint.setTextSize(this.preferredTextSize);
        if (this.textPaint.measureText(text) <= targetWidth) {
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.preferredTextSize);
            return;
        }

        float tempMinTextSize = this.minTextSize;
        float tempPreferredTextSize = this.preferredTextSize;

        while ((tempPreferredTextSize - tempMinTextSize) > threshold) {
            float size = (tempPreferredTextSize + tempMinTextSize) / 2;
            this.textPaint.setTextSize(size);
            if (this.textPaint.measureText(text) >= targetWidth) {
                tempPreferredTextSize = size;
            } else {
                tempMinTextSize = size;
            }
        }

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, tempMinTextSize);

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        this.refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int width, int h, int oldw, int oldh) {
        if (width != oldw) {
            this.refitText(this.getText().toString(), width);
        }
    }
}

