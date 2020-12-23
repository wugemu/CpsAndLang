package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 小程序分享弹窗
 */

public class ShareLittleAppDialog implements View.OnClickListener {

    private Activity activity;
    private Dialog dialog = null;
    private OnShareClickListener mListener;
    public Dialog getDialog() {
        return dialog;
    }

    public ShareLittleAppDialog(Activity activity) {
        this.activity = activity;
        init();
    }

    public void init() {
        View view = LinearLayout.inflate(activity, R.layout.dialog_share_littleapp, null);
        view.findViewById(R.id.little_dismiss).setOnClickListener(this);
        view.findViewById(R.id.dialog_littleapp_share_wechat).setOnClickListener(this);
        view.findViewById(R.id.dialog_littleapp_share_wechat_friends).setOnClickListener(this);
        view.findViewById(R.id.dialog_littleapp_share_qq).setOnClickListener(this);
        view.findViewById(R.id.dialog_littleapp_share_QQZone).setOnClickListener(this);
        view.findViewById(R.id.dialog_layout).setOnClickListener(this);
        view.findViewById(R.id.dialog_layout_02).setOnClickListener(this);
        dialog = new Dialog(activity, R.style.DialogOutsideBg);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏
            View decorView = dialog.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            dialog.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_layout:
            case R.id.little_dismiss:
                dialog.dismiss();
                break;
            case R.id.dialog_layout_02:
                break;
            case R.id.dialog_littleapp_share_wechat:
                if (mListener != null) {
                    mListener.onShareClick("wechat");
                }
                dialog.dismiss();
                break;
            case R.id.dialog_littleapp_share_wechat_friends:
                if (mListener != null) {
                    mListener.onShareClick("wechatFriends");
                }
                dialog.dismiss();
                break;
            case R.id.dialog_littleapp_share_qq:
                if (mListener != null) {
                    mListener.onShareClick("qq");
                }
                dialog.dismiss();
                break;
            case R.id.dialog_littleapp_share_QQZone:
                if (mListener != null) {
                    mListener.onShareClick("qqzone");
                }
                dialog.dismiss();
                break;
        }
    }
    public void setShareClickListener(ShareLittleAppDialog.OnShareClickListener li) {
        mListener = li;
    }
    public  interface OnShareClickListener {
        void onShareClick(String type);
    }
}
