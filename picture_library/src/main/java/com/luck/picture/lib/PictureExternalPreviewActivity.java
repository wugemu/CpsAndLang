package com.luck.picture.lib;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.dialog.CustomDialog;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.observable.SubjectListener;
import com.luck.picture.lib.observable.TouchLongI;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.ToastManage;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.ui
 * email：邮箱->893855882@qq.com
 * data：17/01/18
 */
public class PictureExternalPreviewActivity extends PictureBaseActivity implements View.OnClickListener {
    public static TouchLongI touchLongI;
    private ImageButton left_back;
    private TextView tv_title;
    private PreviewViewPager viewPager;
    private List<LocalMedia> images = new ArrayList<>();
    private int position = 0;
    private String directory_path;
    private SimpleFragmentAdapter adapter;
    private LayoutInflater inflater;
    private RxPermissions rxPermissions;
    private loadDataThread loadDataThread;
    private RelativeLayout rl_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_activity_external_preview);
        inflater = LayoutInflater.from(this);
        rl_title=(RelativeLayout)findViewById(R.id.rl_title);
        tv_title = (TextView) findViewById(R.id.picture_title);
        left_back = (ImageButton) findViewById(R.id.left_back);
        viewPager = (PreviewViewPager) findViewById(R.id.preview_pager);
        position = getIntent().getIntExtra(PictureConfig.EXTRA_POSITION, 0);
        directory_path = getIntent().getStringExtra(PictureConfig.DIRECTORY_PATH);
        images = (List<LocalMedia>) getIntent().getSerializableExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST);
        boolean hiddenTopBar=getIntent().getBooleanExtra(PictureConfig.EXTRA_HIDDEN_TOPBAR,false);
        if(hiddenTopBar){
            rl_title.setVisibility(View.GONE);
        }else {
            rl_title.setVisibility(View.VISIBLE);
        }
        left_back.setOnClickListener(this);
        initViewPageAdapterData();
    }

    private void initViewPageAdapterData() {
        tv_title.setText(position + 1 + "/" + images.size());
        adapter = new SimpleFragmentAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_title.setText(position + 1 + "/" + images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
        overridePendingTransition(0, R.anim.a3);
    }

    public class SimpleFragmentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View contentView = inflater.inflate(R.layout.picture_image_preview, container, false);
            // 常规图控件
            final PhotoView imageView = (PhotoView) contentView.findViewById(R.id.preview_image);
            // 长图控件
            final SubsamplingScaleImageView longImg = (SubsamplingScaleImageView) contentView.findViewById(R.id.longImg);

            LocalMedia media = images.get(position);
            if (media != null) {
                final String pictureType = media.getPictureType();
                final String path;
                if (media.isCut() && !media.isCompressed()) {
                    // 裁剪过
                    path = media.getCutPath();
                } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = media.getCompressPath();
                } else {
                    path = media.getPath();
                }
//                boolean isHttp = PictureMimeType.isHttp(path);
//                // 可以长按保存并且是网络图片显示一个对话框
//                if (isHttp) {
//                    showPleaseDialog();
//                }
                final boolean isGif = PictureMimeType.isGif(pictureType);
                // 压缩过的gif就不是gif了
                if (isGif && !media.isCompressed()) {
                    Glide.with(PictureExternalPreviewActivity.this)
                            .load(path)
                            .override(480, 800)
                            .priority(Priority.HIGH)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    dismissDialog();
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    dismissDialog();
                                    return false;
                                }
                            })
                            .into(imageView);
                } else {
                    Glide.with(PictureExternalPreviewActivity.this)
                            .load(path)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(new CustomTarget<Drawable>(480, 800) {
                                @Override
                                public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                                    dismissDialog();
                                    Bitmap resource=((BitmapDrawable)drawable).getBitmap();
                                    if(resource==null){
                                        return;
                                    }
                                    boolean eqLongImg = PictureMimeType.isLongImg(PictureExternalPreviewActivity.this,resource.getWidth(),resource.getHeight());
                                    imageView.setVisibility(eqLongImg && !isGif ? View.GONE : View.VISIBLE);
                                    longImg.setVisibility(eqLongImg && !isGif ? View.VISIBLE : View.GONE);
                                    if (eqLongImg) {
                                        displayLongPic(resource, longImg);
                                    } else {
                                        int width = ScreenUtils.getScreenWidth(PictureExternalPreviewActivity.this);
                                        int sHeight= ScreenUtils.getScreenHeight(PictureExternalPreviewActivity.this);
                                        int titlebarHeight=(int) getResources().getDimension(R.dimen.titlebar_top);
                                        double imgS=resource.getHeight()*1.0/resource.getWidth();
                                        double screenS=(sHeight-titlebarHeight)*1.0/width;
                                        if(imgS>screenS) {
                                            //图片比例和屏幕比例  图片宽度不够 图片宽度扩大
                                            int height = (int)(1.0*width / resource.getWidth() * resource.getHeight());
                                            if((sHeight-titlebarHeight)>height){
                                                //图片宽度够了 图片高度不够 扩大图片高度
                                                height=sHeight-titlebarHeight;
                                            }
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                                            imageView.setLayoutParams(params);
                                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                        }else {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, sHeight-titlebarHeight);
                                            imageView.setLayoutParams(params);
                                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                        }
                                        imageView.setImageBitmap(resource);
                                    }
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                }
                imageView.setOnViewTapListener(new OnViewTapListener() {
                    @Override
                    public void onViewTap(View view, float x, float y) {
                        finish();
                        overridePendingTransition(0, R.anim.a3);
                    }
                });
                longImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        overridePendingTransition(0, R.anim.a3);
                    }
                });
                longImg.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if(touchLongI!=null){
                            touchLongI.setImgUrl(PictureExternalPreviewActivity.this,view,path);
                        }
                        return false;
                    }
                });
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if(touchLongI!=null){
                            touchLongI.setImgUrl(PictureExternalPreviewActivity.this,v,path);
                        }
//                        if (rxPermissions == null) {
//                            rxPermissions = new RxPermissions(PictureExternalPreviewActivity.this);
//                        }
//                        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                .subscribe(new Observer<Boolean>() {
//                                    @Override
//                                    public void onSubscribe(Disposable d) {
//                                    }
//
//                                    @Override
//                                    public void onNext(Boolean aBoolean) {
//                                        if (aBoolean) {
//                                            showDownLoadDialog(path);
//                                        } else {
//                                            ToastManage.s(mContext, getString(R.string.picture_jurisdiction));
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                    }
//
//                                    @Override
//                                    public void onComplete() {
//                                    }
//                                });
                        return true;
                    }
                });
            }
            (container).addView(contentView, 0);
            return contentView;
        }
    }

    /**
     * 加载长图
     *
     * @param bmp
     * @param longImg
     */
    private void displayLongPic(Bitmap bmp, SubsamplingScaleImageView longImg) {
        longImg.setQuickScaleEnabled(true);
        longImg.setZoomEnabled(true);
        longImg.setPanEnabled(true);
        longImg.setDoubleTapZoomDuration(100);
        longImg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        longImg.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
        longImg.setImage(ImageSource.cachedBitmap(bmp), new ImageViewState(0, new PointF(0, 0), 0));
    }

    /**
     * 下载图片提示
     */
    private void showDownLoadDialog(final String path) {
        final CustomDialog dialog = new CustomDialog(PictureExternalPreviewActivity.this,
                ScreenUtils.getScreenWidth(PictureExternalPreviewActivity.this) * 3 / 4,
                ScreenUtils.getScreenHeight(PictureExternalPreviewActivity.this) / 4,
                R.layout.picture_wind_base_dialog_xml, R.style.Theme_dialog);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_commit = (Button) dialog.findViewById(R.id.btn_commit);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        tv_title.setText(getString(R.string.picture_prompt));
        tv_content.setText(getString(R.string.picture_prompt_content));
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPleaseDialog();
                boolean isHttp = PictureMimeType.isHttp(path);
                if (isHttp) {
                    loadDataThread = new loadDataThread(path);
                    loadDataThread.start();
                } else {
                    // 有可能本地图片
                    try {
                        String dirPath = PictureFileUtils.createDir(PictureExternalPreviewActivity.this,
                                System.currentTimeMillis() + ".png", directory_path);
                        PictureFileUtils.copyFile(path, dirPath);
                        ToastManage.s(mContext, getString(R.string.picture_save_success) + "\n" + dirPath);
                        dismissDialog();
                    } catch (IOException e) {
                        ToastManage.s(mContext, getString(R.string.picture_save_error) + "\n" + e.getMessage());
                        dismissDialog();
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    // 进度条线程
    public class loadDataThread extends Thread {
        private String path;

        public loadDataThread(String path) {
            super();
            this.path = path;
        }

        @Override
        public void run() {
            try {
                showLoadingImage(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 下载图片保存至手机
    public void showLoadingImage(String urlPath) {
        try {
            URL u = new URL(urlPath);
            String path = PictureFileUtils.createDir(PictureExternalPreviewActivity.this,
                    System.currentTimeMillis() + ".png", directory_path);
            byte[] buffer = new byte[1024 * 8];
            int read;
            int ava = 0;
            long start = System.currentTimeMillis();
            BufferedInputStream bin;
            bin = new BufferedInputStream(u.openStream());
            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(path));
            while ((read = bin.read(buffer)) > -1) {
                bout.write(buffer, 0, read);
                ava += read;
                long speed = ava / (System.currentTimeMillis() - start);
            }
            bout.flush();
            bout.close();
            Message message = handler.obtainMessage();
            message.what = 200;
            message.obj = path;
            handler.sendMessage(message);
        } catch (IOException e) {
            ToastManage.s(mContext, getString(R.string.picture_save_error) + "\n" + e.getMessage());
            e.printStackTrace();
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    String path = (String) msg.obj;
                    ToastManage.s(mContext, getString(R.string.picture_save_success) + "\n" + path);
                    dismissDialog();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R.anim.a3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadDataThread != null) {
            handler.removeCallbacks(loadDataThread);
            loadDataThread = null;
        }
    }

    //用户选择允许或拒绝后，会回调onRequestPermissionsResult方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    //根据requestCode和grantResults(授权结果)做相应的后续处理
    private void doNext(int requestCode, int[] grantResults) {

        if (requestCode == 0) {
            if(grantResults==null||grantResults.length==0){
                return;
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "请在设置中打开权限",Toast.LENGTH_LONG).show();
            }
        }
    }
}
