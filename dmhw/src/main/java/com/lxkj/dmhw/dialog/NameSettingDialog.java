package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

import java.util.Objects;

public class NameSettingDialog extends SimpleDialog<String> {

    private EditText dialogNameSettingEdit;

    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public NameSettingDialog(Context context, String data) {
        super(context, R.layout.dialog_name_setting, data, true, false);
    }

    @Override
    protected void convert(ViewHolder helper) {
        dialogNameSettingEdit = helper.getView(R.id.dialog_name_setting_edit);
        helper.setOnClickListener(R.id.dialog_name_setting_close, this);
        helper.setOnClickListener(R.id.dialog_name_setting_confirm, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_name_setting_close:
                hideDialog();
                break;
            case R.id.dialog_name_setting_confirm:
                UserInfo login = DateStorage.getInformation();
                if (dialogNameSettingEdit.getText().toString().length() < 4) {
                    toast("请输入4-16个字符作为昵称！");
                } else {
                    if (Objects.equals(login.getUsername(), dialogNameSettingEdit.getText().toString())) {
                        hideDialog();
                    } else {
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("NameSettingDialog"), dialogNameSettingEdit.getText().toString(), 0);
                        dialogNameSettingEdit.setText("");
                        hideDialog();
                    }
                }
                break;
        }
    }
}
