package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.BaseDialogNew;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.ScoreActivity;

/**
 * 捕捉bug弹框
 */

public class CatchBugDialog extends BaseDialogNew {

    private TextView give_bi_txt;
    public CatchBugDialog(Activity activity) {
        super(activity);
        give_bi_txt=findView(R.id.give_bi_txt);
        findView(R.id.dialog_sign_clean).setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_catch_bug, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_sign_clean:
                dialog.dismiss();
                break;
        }
    }

    public void showDialog(String value) {
        give_bi_txt.setText(value);
        dialog.show();
    }
}
