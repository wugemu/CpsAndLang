package com.lxkj.dmhw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.lxkj.dmhw.R;

public class ArcBackgroundView extends View {
    private int mWidth;
    private int mHeight;
    private int mArcImageViewTopHeight;
    private Path mPath;
    private PathShape mPathShape;
    private int mArcBackgroundViewEndColor;
    private int mArcBackgroundViewStartColor;
    private int[] mColors;
    private LinearGradient mLinearGradient;
    private ShapeDrawable mShapeDrawable;

    public ArcBackgroundView(Context context) {
        this(context, null);
    }

    public ArcBackgroundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ArcBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcBackgroundView);
        mArcBackgroundViewEndColor = typedArray.getColor(R.styleable.ArcBackgroundView_ArcBackgroundViewEndColor, mArcBackgroundViewEndColor);
        mArcBackgroundViewStartColor = typedArray.getColor(R.styleable.ArcBackgroundView_ArcBackgroundViewStartColor, mArcBackgroundViewStartColor);
        mArcImageViewTopHeight = typedArray.getDimensionPixelOffset(R.styleable.ArcBackgroundView_ArcBackgroundViewTopHeight, 0);
        mPath = new Path();
        typedArray.recycle();
        mColors = new int[]{mArcBackgroundViewStartColor, mArcBackgroundViewEndColor};
        mShapeDrawable = new ShapeDrawable();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.quadTo(mWidth/2, mArcImageViewTopHeight * 2,mWidth, 0);
        mPath.lineTo(mWidth,mHeight);
        mPath.lineTo(0,mHeight);
        mPath.close();
        if (mPathShape == null) {
            mPathShape = new PathShape(mPath, mWidth,mHeight);
            mLinearGradient = new LinearGradient(0, 0, 0, mHeight, mColors, null, Shader.TileMode.CLAMP);
        }
        mShapeDrawable.setShape(mPathShape);
        mShapeDrawable.setBounds(0,0,mWidth,mHeight);
        mShapeDrawable.getPaint().setShader(mLinearGradient);
        mShapeDrawable.draw(canvas);
    }
}
