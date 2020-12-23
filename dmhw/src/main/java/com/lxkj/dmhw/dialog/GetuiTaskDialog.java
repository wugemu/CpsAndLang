package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.BaseDialogNew;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.ScoreActivity;
import com.lxkj.dmhw.bean.Sign;

/**
 * 去查看推送任务
 */

public class GetuiTaskDialog extends BaseDialogNew {

    public GetuiTaskDialog(Activity activity) {
        super(activity);
        findView(R.id.dialog_sign_clean).setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_getui_task, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_sign_clean:
                dialog.dismiss();
                break;
        }
    }

    public void showDialog() {
        dialog.show();
    }
}
