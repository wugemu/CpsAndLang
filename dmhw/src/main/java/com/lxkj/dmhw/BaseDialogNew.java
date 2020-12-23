package com.lxkj.dmhw;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.lxkj.dmhw.R;
import com.orhanobut.logger.Logger;

/**
 * dialog父类NEW
 */

public abstract class BaseDialogNew implements View.OnClickListener {

    public View view;
    public Context context;
    public Dialog dialog = null;

    public BaseDialogNew(Context context) {
        try {
            this.context = context;
            view = onInit();
            dialog =new Dialog(context,R.style.DialogTheme11);
            dialog.setContentView(view);
            Window window = dialog.getWindow();
            //设置弹出位置
            window.setGravity(Gravity.CENTER);
            //设置弹出动画
//            window.setWindowAnimations(R.style.super_search_animStyle);
            window.setWindowAnimations(R.style.search_animStyle);

            //设置对话框大小
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.dialogTransparent);
            dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(int id) {
        return (T) view.findViewById(id);
    }

    /**
     * 弹出通知
     * @param content
     */
    public void toast(String content) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 跳转页面
     * @param intent
     */
    public void startActivity(Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 初始化
     * @return xml页面
     */
    abstract public View onInit();
}
