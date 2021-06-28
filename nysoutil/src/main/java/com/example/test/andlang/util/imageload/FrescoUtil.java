package com.example.test.andlang.util.imageload;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import androidx.annotation.Nullable;

import android.view.ViewGroup;

import com.example.test.andlang.R;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created By lhp
 * on 2019/12/2
 */
public class FrescoUtil {

    private static FrescoUtil fresco;
    private int imageDef;
    private GenericDraweeHierarchy genericDraweeHierarchy;

    private FrescoUtil() {
    }

    public static FrescoUtil getInstance() {
        if (fresco == null) {
            fresco = new FrescoUtil();
        }
        return fresco;
    }

    /**
     * 初始化默认图的资源ID
     */
    public void initDefResId(Activity activity) {
        if (imageDef == 0) {
            imageDef = BaseLangUtil.getResFromAttr(activity, R.attr.image_def);
        }
        genericDraweeHierarchy = new GenericDraweeHierarchyBuilder(activity.getResources()).build();
    }

    void updateViewSize(@Nullable ImageInfo imageInfo, SimpleDraweeView draweeView) {
        if (imageInfo != null) {
            draweeView.getLayoutParams().width = imageInfo.getWidth();
            draweeView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            draweeView.setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());
        }
    }

    public void display(final SimpleDraweeView draweeView, String uri, final FrescoWebpAnimSetListener frescoWebpAnimSetListener) {
        genericDraweeHierarchy.setFailureImage(imageDef, ScalingUtils.ScaleType.CENTER);
        draweeView.setHierarchy(genericDraweeHierarchy);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(uri))
                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {

                    }

                    @Override
                    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                        if (animatable!=null){
                            if (frescoWebpAnimSetListener != null) {
                                frescoWebpAnimSetListener.setAnim(animatable);
                            }else{
                                animatable.start();
                            }
                        }
                    }

                    @Override
                    public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                        LogUtil.i("");
                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
//                        genericDraweeHierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
//                        draweeView.setHierarchy(genericDraweeHierarchy);
//                        display(draweeView, "res://" + draweeView.getContext().getPackageName() + "/" + imageDef, null);

                    }

                    @Override
                    public void onRelease(String id) {
                        LogUtil.i("");
                    }
                })
                .build();
        draweeView.setController(controller);
    }


    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
