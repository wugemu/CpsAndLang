package com.lxkj.dmhw.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.ShareInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.FixedCBLoopViewPager.ConvenientBannerImage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.ImageProgressUtil.ProgressInterceptor;
import com.lxkj.dmhw.utils.ImageProgressUtil.ProgressListener;
import com.lxkj.dmhw.utils.Utils;
import com.xx.roundprogressbar.RoundProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageActivity extends BaseActivity implements CBViewHolderCreator {

    @BindView(R.id.image)
    ConvenientBannerImage image;
    @BindView(R.id.image_layout)
    LinearLayout imageLayout;
    @BindView(R.id.image_cancel)
    LinearLayout imageCancel;
    @BindView(R.id.image_save)
    Button imageSave;
    @BindView(R.id.roundbar)
    RoundProgressBar roundbar;
    @BindView(R.id.image_save_iv)
    ImageView image_save_iv;

    // 指示器图标
    private int[] indicator = new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused};
    // 回调名称
    public static String name = "PictureViewer";
    // 图片缓存
    public static Bitmap[] bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        // 设置动画
        overridePendingTransition(R.anim.in_from_fade, R.anim.out_to_fade);
        // 获取图片数据
        try{
            ArrayList<String> netWorkImage = getIntent().getStringArrayListExtra("imageList");
            // 初始化Bitmap
            bitmap = new Bitmap[netWorkImage.size()];
            if (netWorkImage!=null&&netWorkImage.size()>1){
                image.setPointViewVisible(true);
                image.setManualPageable(true);
            }else{
                image.setPointViewVisible(false);
                image.setManualPageable(false);
            }
            // 设置适配器和数据
            image.setPages(this, netWorkImage);
            // 设置指示器
            image.setPageIndicator(indicator);
            // 设置显示位置
            image.setcurrentitem(getIntent().getExtras().getInt("position"));
            // 设置是否循环
            image.setCanLoop(false);
        }catch (Exception e){

        }
    }

    @Override
    public Object createHolder() {
        return new ImageNetWorkAdapter(handler, (ShareInfo) getIntent().getSerializableExtra("info"));
    }

    @OnClick({R.id.image_cancel, R.id.image_save,R.id.image_save_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_cancel:
                imageLayout.setVisibility(View.GONE);
                break;
            case R.id.image_save:
                if (!Utils.checkPermision(this,1011,true)) {
                    return;
                }
                if (bitmap != null) {
                    String content = Utils.saveFile(Variable.picturePath, bitmap[image.getCurrentItem()], 100, true);
                    imageLayout.setVisibility(View.GONE);
                    Toast.makeText(this, "图片已保存至" + content, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.image_save_iv:
                if (!Utils.checkPermision(this,1011,true)) {
                    return;
                }
                if (bitmap != null) {
                    String content = Utils.saveFile(Variable.picturePath, bitmap[image.getCurrentItem()], 100, true);
                    Toast.makeText(this, "图片已保存至" + content, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private class ImageNetWorkAdapter implements Holder<String>, View.OnLongClickListener, PhotoViewAttacher.OnPhotoTapListener {

        private Handler handler;
        private PhotoView imageView;
        private ShareInfo info;

        ImageNetWorkAdapter(Handler handler, ShareInfo info) {
            this.handler = handler;
            this.info = info;
        }

        @Override
        public View createView(Context context) {
            imageView = new PhotoView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.canZoom();
            imageView.setOnLongClickListener(this);
            imageView.setOnPhotoTapListener(this);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            ProgressInterceptor.addListener(data, new ProgressListener() {
                public void onProgress(int progress) {
                    roundbar.setCurrentProgress(progress);
                }
            });

            if (getIntent().hasExtra("isCheck")) {
                if (getIntent().getBooleanExtra("isCheck", false)) {
                    Glide.with(ImageActivity.this).asBitmap()
                            .load(data).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //第一张不绘制图片
//                                       if (position == 0) {
//                                        if (DateStorage.getLoginStatus()) {
//                                            if (Utils.jointBitmapNew(context, resource, info)!=null) {
//                                                bitmap[0] = Utils.scaleWidth(Utils.jointBitmapNew(context, resource, info), 1080);
//                                            }
//                                        }else{
//                                            bitmap[0] = Utils.scaleWidth(resource, width);
//                                        }
//                                    } else {
//                                        bitmap[position] = Utils.scaleWidth(resource, width);
//                                    }
                                     bitmap[position] = Utils.scaleWidth(resource, width);

                                    if (bitmap[position].getHeight() > Utils.dipToPixel(R.dimen.dp_640)) {
                                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    } else {
                                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    }
                                    imageView.setImageBitmap(bitmap[position]);
                                                 }
                                             });

                } else {
                    Glide.with(ImageActivity.this).asBitmap()
                            .load(data)
                           .into(new SimpleTarget<Bitmap>()  {
                               @Override
                               public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                   ProgressInterceptor.removeListener(data);
                                   roundbar.setVisibility(View.GONE);
                                   bitmap[position] = Utils.jointBitmapNew(context, resource, info);
                                   if (bitmap[position] != null) {
                                       if (bitmap[position].getHeight() > Utils.dipToPixel(R.dimen.dp_640)) {
                                           imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                       } else {
                                           imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                       }
                                       imageView.setImageBitmap(bitmap[position]);
                                   }
                               }
                               });
                }
            } else {
                Glide.with(ImageActivity.this).asBitmap()
                        .load(data).into(new SimpleTarget<Bitmap>()
                       {
                           @Override
                           public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                               ProgressInterceptor.removeListener(data);
                               roundbar.setVisibility(View.GONE);
                               bitmap[position] = Utils.scaleWidth(resource, width);
                               if (bitmap[position].getHeight() > Utils.dipToPixel(R.dimen.dp_640)) {
                                   imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                               } else {
                                   imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                               }
                               imageView.setImageBitmap(bitmap[position]);
                           }
                           });
            }
        }

        @Override
        public boolean onLongClick(View v) {
            sendHandlerMsg(handler, LogicActions.getActionsSuccess(name), false, 0);
            return true;
        }

        @Override
        public void onPhotoTap(View view, float v, float v1) {
            sendHandlerMsg(handler, LogicActions.getActionsSuccess(name), true, 0);
        }

        @Override
        public void onOutsidePhotoTap() {
            isFinish();
        }

        /**
         * 数据回调UI
         *
         * @param handler
         * @param what
         * @param object
         */
        void sendHandlerMsg(Handler handler, int what, Object object, int error) {
            Message message = new Message();
            message.what = what;
            message.obj = object;
            message.arg1 = error;
            handler.sendMessage(message);
        }
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LogicActions.PictureViewerSuccess) {
                if ((boolean) msg.obj) {
                    isFinish();
                } else {
                    imageLayout.setVisibility(View.VISIBLE);
                }
            }
        }

    };

    /**
     * 关闭页面
     */
    public void isFinish() {
        if (imageLayout.getVisibility() == View.VISIBLE) {
            imageLayout.setVisibility(View.GONE);
        } else {
            finish();
            // 设置动画
            overridePendingTransition(R.anim.in_from_fade, R.anim.out_to_fade);
            ActivityManagerDefine.getInstance().popActivity(this);//退出页面时调用
        }
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {

    }

}
