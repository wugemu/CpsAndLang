package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.BaseDialogNew;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

/**
 * 注册
 */

public class SearchTaskDialog extends BaseDialogNew {

    private LinearLayout dialog_register_goto;
    private TextView give_bi_txt;
    public SearchTaskDialog(Activity activity) {
        super(activity);
        give_bi_txt=findView(R.id.give_bi_txt);
        findView(R.id.dialog_register_goto).setOnClickListener(this);
        findView(R.id.dialog_sign_clean).setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_search_task, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_register_goto:
                dialog.dismiss();
                break;
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
