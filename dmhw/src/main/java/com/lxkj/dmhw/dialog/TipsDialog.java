package com.lxkj.dmhw.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

public class TipsDialog implements View.OnClickListener {

    private View view;
    private String name;
    private Dialog dialog = null;
    private TextView dialogTipsTitle;
    private TextView dialogTipsContent;
    private TextView dialogTipsConfirmText;
    private LinearLayout dialogTipsCancel;

    public TipsDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        view = LinearLayout.inflate(context, R.layout.dialog_tips, null);
        dialogTipsTitle = findView(R.id.dialog_tips_title);
        dialogTipsContent = findView(R.id.dialog_tips_content);
        dialogTipsConfirmText = findView(R.id.dialog_tips_confirm_text);
        dialogTipsCancel = findView(R.id.dialog_tips_cancel);
        findView(R.id.dialog_tips_confirm).setOnClickListener(this);
        findView(R.id.dialog_tips_cancel).setOnClickListener(this);
        dialog = new Dialog(context, R.style.myTransparent);
        dialog.setOnKeyListener(keyListener);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        // 设置状态栏透明,5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏
            View decorView = dialog.getWindow().getDecorView();
            int option, color;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = Color.TRANSPARENT;
                option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                color = Color.parseColor("#99000000");
                option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }
            decorView.setSystemUiVisibility(option);
            dialog.getWindow().setStatusBarColor(color);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T findView(int id) {
        return (T) view.findViewById(id);
    }

    /**
     * 监听返回键
     */
    private DialogInterface.OnKeyListener keyListener = (dialog, keyCode, event) -> {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        } else {
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_tips_confirm:// 确认
                if (!TextUtils.isEmpty(name)) {
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess(name), "", 0);
                }
                dialog.dismiss();
                break;
            case R.id.dialog_tips_cancel:// 取消
                dialog.dismiss();
                break;
        }
    }

    /**
     * 显示弹窗
     * @param name 回调名字
     * @param title 提示标题
     * @param content 提示内容
     */
    public void showDialog(String name, String title, String content) {
        this.name = name;
        dialogTipsTitle.setText(title);
        dialogTipsContent.setText(content);
        dialog.show();
    }

    /**
     * 显示弹窗
     * @param name 回调名字
     * @param title 提示标题
     * @param content 提示内容
     * @param btn 按钮文字
     */
    public void showDialog(String name, String title, String content, String btn) {
        this.name = name;
        dialogTipsTitle.setText(title);
        dialogTipsContent.setText(content);
        dialogTipsConfirmText.setText(btn);
        dialogTipsCancel.setVisibility(View.GONE);
        dialog.show();
    }
}
