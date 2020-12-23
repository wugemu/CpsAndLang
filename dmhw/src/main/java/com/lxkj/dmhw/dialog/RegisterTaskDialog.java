package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.BaseDialogNew;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.MyTaskFragmentActivity;

/**
 * 注册
 */

public class RegisterTaskDialog extends BaseDialogNew {

    private LinearLayout dialog_register_goto;
    private TextView give_bi_txt;
    public RegisterTaskDialog(Activity activity) {
        super(activity);
        give_bi_txt=findView(R.id.give_bi_txt);
        findView(R.id.dialog_register_goto).setOnClickListener(this);
        findView(R.id.dialog_sign_clean).setOnClickListener(this);
        findView(R.id.register_layout_root).setOnClickListener(this);
        findView(R.id.register_layout_root_01).setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_register_task, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_register_goto:
               Intent intent = new Intent(context, MyTaskFragmentActivity.class);
               startActivity(intent);
                dialog.dismiss();
                break;
            case R.id.register_layout_root:
                dialog.dismiss();
                break;
            case R.id.register_layout_root_01:
                break;
        }
    }

    public void showDialog(String value) {
        give_bi_txt.setText(Variable.registScore);
        dialog.show();
    }
}
