package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.test.andlang.andlangutil.BaseLangApplication;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.StatusBarUtils;
import com.example.test.andlang.util.imageload.GlideUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lang on 17-2-27.
 * 查看大图
 */
public class LookPicDialog {
    private Dialog overdialog;
    @BindView(R.id.iv_image)
    ImageView imageView;
    private Activity activity;

    public LookPicDialog(Activity context, String imgUrl) {
        this.activity = context;
        View view = View.inflate(context, R.layout.dialog_look_picture, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp3);
        overdialog.setContentView(view);
//        GlideUtil.getInstance().displayNoBg(context, imgUrl, imageView);
        ImageLoadUtils.doLoadCircleImageUrl(imageView,imgUrl);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overdialog.dismiss();
            }
        });
        showDialog();

    }

    public void showDialog() {
        if (overdialog != null) {
            overdialog.show();
            Window win = overdialog.getWindow();
            WindowManager m = activity.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
            win.getDecorView().setPadding(0, 0, 0, 0);
            p.height = d.getHeight();
            p.width = d.getWidth(); // 宽度设置为屏幕的0.85
            win.setAttributes(p);

        } else {
            overdialog.show();
        }
    }
}