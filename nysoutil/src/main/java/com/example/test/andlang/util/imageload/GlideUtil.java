package com.example.test.andlang.util.imageload;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.test.andlang.R;
import com.example.test.andlang.andlangutil.BaseLangApplication;
import com.example.test.andlang.util.BaseLangUtil;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 1 on 2018/1/16.
 */
public class GlideUtil {

    private static GlideUtil glide;
    private int imageDef;
    private String headDef;
    private GlideUtil() {
    }

    public static GlideUtil getInstance() {
        if (glide == null) {
            glide = new GlideUtil();
        }
        return glide;
    }

    /**
     * 初始化默认图的资源ID
     */
    public void initDefResId(Activity activity,String noBgDef) {
        if (imageDef == 0) {
            imageDef = BaseLangUtil.getResFromAttr(activity, R.attr.image_def);
        }
        this.headDef=noBgDef;
        FrescoUtil.getInstance().initDefResId(activity);
    }

//    private void displayHead(final Context context, String url, final ImageView imageView, final int imageDef) {
//        Glide.with(context).load(url)
//                .dontAnimate()//不做淡出效果
//                .transform(new GlideCircleTransform(context))
//                .into(new GlideDrawableImageViewTarget(imageView) {
//
//                    @Override
//                    public void onLoadStarted(Drawable placeholder) {
//                        // 开始加载图片
//
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        imageView.setScaleType(ImageView.ScaleType.CENTER);
//                        imageView.setBackgroundColor(context.getResources().getColor(R.color.lang_colorBackGroud));
//                        imageView.setImageResource(imageDef);
//                    }
//
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                        super.onResourceReady(resource, glideAnimation);
//                        imageView.setBackgroundColor(Color.TRANSPARENT);
//                        // 图片加载完成
//                    }
//                });
//
//
//    }

//    public void displayHead(final Context context, String url, final ImageView imageView) {
//        displayHead(context, url, imageView, imageDef);
//    }

//    public void displayBitmap(final Context context, String url, final ImageView imageView){
//        Glide.with(context).load(url).asBitmap().into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                imageView.getLayoutParams().width=resource.getWidth();
//                imageView.getLayoutParams().height=resource.getHeight();
//                imageView.setImageBitmap(resource);
//                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//               }
//        });
//    }

    public void displayByScreenWidth(final Context context, String url, final ImageView imageView, final int screenWidth) {
        Glide.with(context).load(url).asBitmap().into(new BitmapImageViewTarget(imageView) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                // 开始加载图片
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setImageDrawable(placeholder);
                imageView.setBackgroundColor(context.getResources().getColor(R.color.lang_colorBackGroud));
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setImageResource(imageDef);
                imageView.setBackgroundColor(context.getResources().getColor(R.color.lang_colorBackGroud));
            }


            @Override
            protected void setResource(Bitmap resource) {
                double kgb = resource.getHeight() * 1.0 / resource.getWidth();
                imageView.getLayoutParams().height = (int) (screenWidth * kgb);
                imageView.setImageBitmap(resource);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setBackgroundColor(Color.TRANSPARENT);
            }
        });
    }

//    public void displayBrand(final Context context, String url, final ImageView imageView){
//        Glide.with(context).load(url).asBitmap().placeholder(R.mipmap.image_def).centerCrop().into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                imageView.setImageDrawable(createRoundImageWithBorder(context,resource,(int)context.getResources().getDimension(R.dimen.lang_broder2), R.color.lang_colorHint));
//            }
//        });
//    }

    public void displayNoDefBackground(final Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url)
                .placeholder(imageDef)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        // 开始加载图片
                        imageView.setScaleType(ImageView.ScaleType.CENTER);
                        imageView.setImageDrawable(placeholder);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        imageView.setScaleType(ImageView.ScaleType.CENTER);
                        imageView.setImageResource(imageDef);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        super.onResourceReady(resource, glideAnimation);
                        // 图片加载完成
                    }
                });

    }

    public void displayPlace(final Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        imageView.setScaleType(ImageView.ScaleType.CENTER);
                        imageView.setImageResource(imageDef);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        imageView.setBackgroundColor(context.getResources().getColor(R.color.lang_colorBackGroud));
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        // 图片加载完成
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                });

    }

    /**
     * 最通用型图片显示方法
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void display(Context context, String url, ImageView imageView) {
        if (url.toLowerCase().contains(".gif")) {
            GlideUtil.getInstance().displayGif(context, url, imageView, ImageView.ScaleType.FIT_XY);
        } else if (url.toLowerCase().contains(".webp")){
            FrescoUtil.getInstance().display((SimpleDraweeView) imageView,url,null);
        }else{
            GlideUtil.getInstance().display(context, url, imageView, imageDef, ImageView.ScaleType.FIT_XY);
        }
    }


    /**
     * 通用型显示gif
     *
     * @param context
     * @param url
     * @param imageView
     * @param scaleType
     */
    public void displayGif(final Context context, String url, final ImageView imageView, final ImageView.ScaleType scaleType) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(imageView, GlideDrawable.LOOP_FOREVER) {

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        // 开始加载图片
                        imageView.setBackgroundColor(context.getResources().getColor(R.color.lang_colorWhite));
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        imageView.setBackgroundColor(context.getResources().getColor(R.color.lang_colorBackGroud));
                        imageView.setScaleType(ImageView.ScaleType.CENTER);
                        imageView.setImageResource(imageDef);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setScaleType(scaleType);
                        super.onResourceReady(resource, glideAnimation);
                        imageView.setBackgroundColor(Color.TRANSPARENT);
                        // 图片加载完成
                    }
                });
    }

    /**
     * 通用型显示普通图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param defaultImgeId
     * @param scaleType
     */
    private void display(final Context context, final String url, final ImageView imageView, final int defaultImgeId, final ImageView.ScaleType scaleType) {
        Glide.with(context).load(url)
                .dontAnimate()//不做淡出效果
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//解决部分机型背景为绿色的问题
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        imageView.setBackgroundColor(context.getResources().getColor(R.color.lang_colorWhite));
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        imageView.setBackgroundColor(context.getResources().getColor(R.color.lang_colorBackGroud));
                        imageView.setScaleType(ImageView.ScaleType.CENTER);
                        imageView.setImageResource(defaultImgeId);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        // 图片加载完成
                        imageView.setScaleType(scaleType);
                        imageView.setBackgroundColor(Color.TRANSPARENT);
                        super.onResourceReady(resource, glideAnimation);

                    }
                });

    }

    public void displayFitCenter(final Context context, final String url, final ImageView imageView) {
        display(context, url, imageView, imageDef, ImageView.ScaleType.FIT_CENTER);
    }

    public void displayCenterCrop(final Context context, final String url, final ImageView imageView) {
        display(context, url, imageView, imageDef, ImageView.ScaleType.CENTER_CROP);
    }

//    private void displayRoundImg(final Context context, String url, final ImageView imageView, final float round, final int imageDef) {
//        displayRoundImg(context, url, imageView, BitmapFillet.CornerType.ALL, round, imageDef);
//    }


    /**
     * 显示部分圆角
     * @param context
     * @param url
     * @param imageView
     * @param imageDef
     * @param backRes
     */
    private void displayRoundImg(final Context context, String url, final ImageView imageView, final int imageDef, final int backRes) {
        Glide.with(context).load(url)
                .asBitmap()
                .dontAnimate()//不做淡出效果
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//解决部分机型背景为绿色的问题
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.lang_colortransparent));
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        if (backRes==0){
                            view.setBackgroundColor(context.getResources().getColor(R.color.lang_colorBackGroud));
                        }else{
                            view.setBackgroundResource(backRes);
                        }
                        view.setScaleType(ImageView.ScaleType.CENTER);
                        view.setImageResource(imageDef);
                    }

                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
//
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//
//                        circularBitmapDrawable.setCornerRadius(round); //设置圆角弧度
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        view.setBackgroundColor(Color.TRANSPARENT);
                        view.setImageBitmap(resource);
                    }
                });

    }


    /**
     * 显示全部圆角
     * @param context
     * @param url
     * @param imageView
     * @param round
     * @param imageDef
     * @param backRes
     */
    private void displayRoundImg(final Context context, String url, final ImageView imageView,final float round,final int imageDef, final int backRes) {
        Glide.with(context).load(url)
                .asBitmap()
                .dontAnimate()//不做淡出效果
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//解决部分机型背景为绿色的问题
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.lang_colortransparent));
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        if (backRes==0){
                            view.setBackgroundColor(context.getResources().getColor(R.color.lang_colorBackGroud));
                        }else{
                            view.setBackgroundResource(backRes);
                        }
                        view.setScaleType(ImageView.ScaleType.CENTER);
                        view.setImageResource(imageDef);
                    }

                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(round); //设置圆角弧度
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        view.setBackgroundColor(Color.TRANSPARENT);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });

    }

    private void displayRoundImg(final Context context, String url, final ImageView imageView, final BitmapFillet.CornerType cornerType, final int width,final int height,final float round, final int imageDef) {

        Glide.with(context).load(url).asBitmap()
//                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()//不做淡出效果
                .into(new BitmapImageViewTarget(imageView) {

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        view.setBackgroundResource(R.color.lang_colortransparent);
//                        imageView.setBackgroundColor(context.getResources().getColor(R.color.lang_colorWhite));
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        // 创建一张控件大小的Bitmap
                        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); // 创建画布
                        bitmap.eraseColor(context.getResources().getColor(R.color.lang_colorBackGroud)); // 填充颜色
                        BitmapFillet bitmapFillet = new BitmapFillet();
                        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmapFillet.fillet(cornerType, bitmap, round, bitmap.getWidth(), bitmap.getHeight()));
                        view.setBackground(bd);
                        view.setScaleType(ImageView.ScaleType.CENTER);
                        view.setImageResource(imageDef);

                    }

                    @Override
                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BitmapFillet bitmapFillet = new BitmapFillet();
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        view.setImageBitmap(bitmapFillet.fillet(cornerType, resource, round, width, height));
                    }
                });
    }
    public void displayRoundImg(final Context context, String url, final ImageView imageView,int backRes) {
        displayRoundImg(context, url, imageView, imageDef,backRes);
    }

    public void displayRoundImg(final Context context, String url, final float round,final ImageView imageView,int backRes) {
        displayRoundImg(context, url, imageView,round, imageDef,backRes);
    }

    public void displayRoundImg(final Context context, String url, final ImageView imageView,int width,final int height, final float round) {
        displayRoundImg(context, url, imageView, BitmapFillet.CornerType.ALL, width, height,round, imageDef);
    }

    public void displayRoundImg(final Context context, String url, final ImageView imageView, BitmapFillet.CornerType cornerType,int width,final int height, final float round) {
        displayRoundImg(context, url, imageView, cornerType,width, height, round, imageDef);
    }

    public void displayLocGif(Context context, int imgId, ImageView imageView) {
        Glide.with(context)
                .load(imgId)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    public void displayLocRes(Context context, int imgId, ImageView imageView) {
        Glide.with(context)
                .load(imgId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

//
//    public void displayHeadNoBg(final Context context, String url, final ImageView imageView) {
//        Glide.with(context)
//                .load(url)
//                .error(imageDef)
//                .placeholder(imageDef)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(new GlideDrawableImageViewTarget(imageView) {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        super.onResourceReady(resource, glideAnimation);
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        imageView.setBackgroundResource(R.drawable.circle_bg);
//                    }
//                });
//    }

    public void displayNoBg(final Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .skipMemoryCache(false)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (!BaseLangUtil.isEmpty(BaseLangApplication.imageDefUrl)){
                            displayNoBg(context,BaseLangApplication.imageDefUrl,imageView);
                        }
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public void displayWeex(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .dontAnimate()//不做淡出效果
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public void displayGifWeex(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
//    private Drawable createRoundImageWithBorder(Context context, Bitmap bitmap, int borderWidthHalf, int borderColor){
//        //原图宽度
//        int bitmapWidth = bitmap.getWidth();
//        //原图高度
//        int bitmapHeight = bitmap.getHeight();
//        //边框宽度 pixel
////        int borderWidthHalf = (int)context.getResources().getDimension(R.dimen.head_broder);
//
//        //转换为正方形后的宽高
//        int bitmapSquareWidth = Math.min(bitmapWidth,bitmapHeight);
//
//        //最终图像的宽高
//        int newBitmapSquareWidth = bitmapSquareWidth+borderWidthHalf;
//
//        Bitmap roundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth,newBitmapSquareWidth, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(roundedBitmap);
//        int x = borderWidthHalf + bitmapSquareWidth - bitmapWidth;
//        int y = borderWidthHalf + bitmapSquareWidth - bitmapHeight;
//
//        //裁剪后图像,注意X,Y要除以2 来进行一个中心裁剪
//        canvas.drawBitmap(bitmap, x/2, y/2, null);
//        Paint borderPaint = new Paint();
//        borderPaint.setStyle(Paint.Style.STROKE);
//        borderPaint.setStrokeWidth(borderWidthHalf);
//        borderPaint.setColor(borderColor);
//        borderPaint.setAntiAlias(true);
//        //添加边框
//        canvas.drawCircle(canvas.getWidth()/2, canvas.getWidth()/2, newBitmapSquareWidth/2, borderPaint);
//
//        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),roundedBitmap);
//        roundedBitmapDrawable.setGravity(Gravity.CENTER);
//        roundedBitmapDrawable.setCircular(true);
//        return roundedBitmapDrawable;
//    }

    // 与宽度一致的正方形
    public void loadImageToBitmap(final Context context, String url, SimpleTarget<Bitmap> target) {
        Glide.with(context).load(url).asBitmap().into(target);
    }

    public Bitmap downImage(Context context, String url) {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(100, 100)
                    .get();
        } catch (Exception e) {

        }
        return bitmap;
    }


}
