package com.lxkj.dmhw.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.utils.Utils;

public class SaveDialog extends SimpleDialog<String> {

    /**
     * 初始化
     */
    public SaveDialog(Context context) {
        super(context, R.layout.dialog_save, "", true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        helper.setOnClickListener(R.id.dialog_save_cancel, this);
        helper.setOnClickListener(R.id.dialog_save_confirm, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_save_cancel:
                hideDialog();
                break;
            case R.id.dialog_save_confirm:
                Utils.openWechat(context);
                break;
        }
    }
}
