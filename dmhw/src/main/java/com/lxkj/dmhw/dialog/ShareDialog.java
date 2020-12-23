package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

/**
 * 代理分享设置
 * Created by Administrator on 2017/12/22 0022.
 */

public class ShareDialog implements View.OnClickListener {

    private Activity activity;
    private Dialog dialog = null;

    public Dialog getDialog() {
        return dialog;
    }

    public ShareDialog(Activity activity) {
        this.activity = activity;
        init();
    }

    public void init() {
        View view = LinearLayout.inflate(activity, R.layout.dialog_share, null);
        view.findViewById(R.id.dialog_close).setOnClickListener(this);
        view.findViewById(R.id.dialog_link).setOnClickListener(this);
        view.findViewById(R.id.dialog_countersign).setOnClickListener(this);
        view.findViewById(R.id.dialog_copywriter).setOnClickListener(this);
        view.findViewById(R.id.dialog_countersign_only).setOnClickListener(this);
        dialog = new Dialog(activity, R.style.myTransparent);
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
            case R.id.dialog_close:
                dialog.dismiss();
                break;
            case R.id.dialog_link:
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareDialog"), "短链接", 0);
                dialog.dismiss();
                break;
            case R.id.dialog_countersign:
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareDialog"), "淘口令", 0);
                dialog.dismiss();
                break;
            case R.id.dialog_copywriter:
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareDialog"), "仅文案", 0);
                dialog.dismiss();
                break;
            case R.id.dialog_countersign_only:
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareDialog"), "仅淘口令", 0);
                dialog.dismiss();
                break;
        }
    }
}
