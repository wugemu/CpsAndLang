package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.test.andlang.util.imageload.GlideUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.luck.picture.lib.PictureExternalPreviewActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.ADBean;
import com.lxkj.dmhw.myinterface.CancelDialogI;
import com.lxkj.dmhw.myinterface.ClickDialogI;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.SDJumpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ADImgDialog {
    @BindView(R.id.iv_adimg)
    ImageView iv_adimg;

    private Dialog adDialog;
    private Activity activity;
    private CancelDialogI cancelDialogI;
    private ClickDialogI clickDialogI;
    private ADBean adBean;

    public ADImgDialog(Activity activity,ADBean adBean,CancelDialogI cancelDialogI) {
        this.activity = activity;
        this.cancelDialogI=cancelDialogI;
        this.adBean=adBean;
        initView();
    }

    public ADImgDialog(Activity activity,ADBean adBean, ClickDialogI clickDialogI) {
        this.activity = activity;
        this.clickDialogI=clickDialogI;
        this.adBean=adBean;
        initView();
    }

    private void initView() {
        if (activity.isDestroyed()) {
            return;
        }
        View overdiaView = View.inflate(activity, R.layout.dialog_adimg, null);
        ButterKnife.bind(this, overdiaView);
        adDialog = new Dialog(activity, R.style.dialog_lhp2);
        adDialog.setContentView(overdiaView);
        if(!BBCUtil.isEmpty(adBean.getImgUrl())){
            Glide.with(activity)
                    .load(adBean.getImgUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                            Bitmap resource=((BitmapDrawable)drawable).getBitmap();
                            if(adBean.getFlag()==2) {
                                //flag=1预缓存 flag=2时显示
                                if (resource != null) {
                                    int newWidth= (int)(BBCUtil.getDisplayWidth(activity)*0.85);
                                    int width = resource.getWidth();
                                    int height = resource.getHeight();
                                    RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(newWidth, newWidth*height/width);
                                    iv_adimg.setLayoutParams(params);
                                }
                                ImageLoadUtils.doLoadImageUrl(iv_adimg,adBean.getImgUrl());
                                showDialog();
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
            });
        }
    }

    private void showDialog() {
        if (!activity.isFinishing())//xActivity即为本界面的Activity
        {
            if (adDialog != null) {
                adDialog.show();
                Window win = adDialog.getWindow();
                WindowManager m = activity.getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
                win.getDecorView().setPadding(0, 0, 0, 0);
                p.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度设置为屏幕的0.6
                p.width = (int) (d.getWidth()*0.85); // 宽度设置为屏幕的0.65
                win.setAttributes(p);
            }
        }
    }

    @OnClick(R.id.iv_adimg)
    public void clickADimg(){
        //点击广告图片
        if(clickDialogI!=null){
            clickDialogI.clickImg();
        }else {
            if (adBean != null) {
                if (!BBCUtil.isEmpty(adBean.getLinkUrl())) {
                    SDJumpUtil.goWhere(activity, adBean.getLinkUrl());
                }
            }
        }
        clickClose();
    }

    @OnClick(R.id.iv_close)
    public void clickClose(){
        adDialog.dismiss();
        if(cancelDialogI!=null){
            cancelDialogI.cancelDialog();
        }
        if(clickDialogI!=null){
            clickDialogI.cancelDialog();
        }
    }
}
