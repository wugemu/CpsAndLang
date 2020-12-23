package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.BaseDialogNew;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Sign;

/**
 * 签到
 */

public class SignDialog extends BaseDialogNew {

    private Sign sign;
    private TextView give_bi_txt;

    public SignDialog(Activity activity) {
        super(activity);
        give_bi_txt=findView(R.id.give_bi_txt);
        findView(R.id.dialog_sign_clean).setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_sign, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_sign_clean:
                dialog.dismiss();
                break;
        }
    }

    public void showDialog(String count) {
        give_bi_txt.setText("奖励" +count + "个NN币");
        dialog.show();
    }
}
