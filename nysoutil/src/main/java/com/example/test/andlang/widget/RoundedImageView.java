package com.example.test.andlang.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.test.andlang.R;
import com.example.test.andlang.util.BaseLangUtil;


public class RoundedImageView extends AppCompatImageView {
    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {16.0f,16.0f,16.0f,16.0f,0.0f,0.0f,0.0f,0.0f};
    private static final String  ALL="ALL";
    private static final String  TOP_LEFT="TOP_LEFT";
    private static final String  TOP_RIGHT="TOP_RIGHT";
    private static final String  BOTTOM_LEFT="BOTTOM_LEFT";
    private static final String  BOTTOM_RIGHT="BOTTOM_RIGHT";
    private static final String  TOP="TOP";
    private static final String  BOTTOM="BOTTOM";
    private static final String  LEFT="LEFT";
    private static final String  RIGHT="RIGHT";
    private static final String  OTHER_TOP_LEFT="OTHER_TOP_LEFT";
    private static final String  OTHER_TOP_RIGHT="OTHER_TOP_RIGHT";
    private static final String  OTHER_BOTTOM_LEFT="OTHER_BOTTOM_LEFT";
    private static final String  OTHER_BOTTOM_RIGHT="OTHER_BOTTOM_RIGHT";
    private static final String  DIAGONAL_FROM_TOP_LEFT="DIAGONAL_FROM_TOP_LEFT";
    private static final String  DIAGONAL_FROM_TOP_RIGHT="DIAGONAL_FROM_TOP_RIGHT";
    private final RectF roundRect = new RectF();
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();

    public RoundedImageView(Context context) {
        this(context,null,0);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundedImageView, defStyleAttr, 0);
        String type= a.getString( R.styleable.RoundedImageView_type);
        if (BaseLangUtil.isEmpty(type)){
            type="ALL";
        }

        float conner = a.getDimensionPixelSize(
                R.styleable.RoundedImageView_conner, 0);
        switch (type){
            case ALL:
                rids =new float[]{conner,conner,conner,conner,conner,conner,conner,conner};
                break;
            case TOP_LEFT:
                rids =new float[]{conner,conner,0,0,0,0,0,0};
                break;
            case TOP_RIGHT:
                rids =new float[]{0,0,conner,conner,0,0,0,0};
                break;
            case BOTTOM_LEFT:
                rids =new float[]{0,0,0,0,0,0,conner,conner};
                break;
            case BOTTOM_RIGHT:
                rids =new float[]{0,0,0,0,conner,conner,0,0};
                break;
            case TOP:
                rids =new float[]{conner,conner,conner,conner,0,0,0,0};
                break;
            case BOTTOM:
                rids =new float[]{0,0,0,0,conner,conner,conner,conner};
                break;
            case LEFT:
                rids =new float[]{conner,conner,0,0,0,0,conner,conner};
                break;
            case RIGHT:
                rids =new float[]{0,0,conner,conner,conner,conner,0,0};
                break;
            case OTHER_TOP_LEFT:
                rids =new float[]{0,0,conner,conner,conner,conner,conner,conner};
                break;
            case OTHER_TOP_RIGHT:
                rids =new float[]{conner,conner,0,0,conner,conner,conner,conner};
                break;
            case OTHER_BOTTOM_LEFT:
                rids =new float[]{conner,conner,conner,conner,conner,conner,0,0};
                break;
            case OTHER_BOTTOM_RIGHT:
                rids =new float[]{conner,conner,conner,conner,0,0,conner,conner};
                break;
            case DIAGONAL_FROM_TOP_LEFT:
                rids =new float[]{conner,conner,0,0,conner,conner,0,0};
                break;
            case DIAGONAL_FROM_TOP_RIGHT:
                rids =new float[]{0,0,conner,conner,0,0,conner,conner};
                break;
            default:
                rids =new float[]{conner,conner,conner,conner,conner,conner,conner,conner};
                break;
        }

        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);

        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), rids, Path.Direction.CW);
        Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setColor(Color.WHITE); // 不要有透明度。
        canvas.drawPath(path, bitmapPaint);

//        canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }

//    private Bitmap makeRoundRectFrame(int w, int h) {
//        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bm);
//        Path path = new Path();
//        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
//        Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        bitmapPaint.setColor(Color.WHITE); // 颜色随意，不要有透明度。
//        c.drawPath(path, bitmapPaint);
//        return bm;
//    }
//
//    /**
//     * 画图
//     * by Hankkin at:2015-08-30 21:15:53
//     * @param canvas
//     */
//    protected void onDraw(Canvas canvas) {
////        Path path = new Path();
////        int w = this.getWidth();
////        int h = this.getHeight();
////        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
////        path.addRoundRect(new RectF(0,0,w,h),rids,Path.Direction.CW);
////        canvas.clipPath(path);
////        super.onDraw(canvas);
//
//
//        // 得到原始的图片
//        final int w = getWidth();
//        final int h = getHeight();
//        Bitmap bitmapOriginal = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bitmapOriginal);
//        c.drawColor(Color.WHITE);
//        super.onDraw(c);
//
//        if (bitmapFrame == null) {
//            bitmapFrame = makeRoundRectFrame(w, h);
//        }
//
//        int sc = canvas.saveLayer(0, 0, w, h, null, Canvas.ALL_SAVE_FLAG);
//
//        canvas.drawBitmap(bitmapFrame, 0, 0, bitmapPaint);
//        // 利用Xfermode取交集（利用bitmapFrame作为画框来裁剪bitmapOriginal）
//        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmapOriginal, 0, 0, bitmapPaint);
//
//        bitmapPaint.setXfermode(null);
//        canvas.restoreToCount(sc);
//    }
}
