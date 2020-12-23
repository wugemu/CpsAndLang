package com.lxkj.dmhw;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * dialog父类
 * Created by zyhant on 18-2-26.
 */

public abstract class BaseDialog implements View.OnClickListener {

    public View view;
    public Context context;
    public Dialog dialog = null;

    public BaseDialog(Context context) {
        try {
            this.context = context;
            view = onInit();
            dialog = new Dialog(context, R.style.myTransparent);
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
