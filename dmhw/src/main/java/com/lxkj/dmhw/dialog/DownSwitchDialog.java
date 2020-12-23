package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

public class DownSwitchDialog extends SimpleDialog<String> {
    /**
     * 初始化
     *
     * @param context  上下文
     * @param data     数据
     */
    public DownSwitchDialog(Context context, String data) {
        super(context, R.layout.dialog_down_switch, data, true, false);
    }

    @Override
    protected void convert(ViewHolder helper) {
        helper.setOnClickListener(R.id.dialog_close, this);
        helper.setOnClickListener(R.id.dialog_down_switch_yes, this);
        helper.setOnClickListener(R.id.dialog_down_switch_no, this);
        helper.setOnClickListener(R.id.dialog_layout_root, this);
        helper.setOnClickListener(R.id.dialog_layout_root_01, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_down_switch_yes:
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess(data), "显示", 0);
                hideDialog();
                break;
            case R.id.dialog_down_switch_no:
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess(data), "不显示", 0);
                hideDialog();
                break;
            case R.id.dialog_close:
                hideDialog();
                break;
            case R.id.dialog_layout_root:
                hideDialog();
                break;
            case R.id.dialog_layout_root_01:
                break;
        }
    }
}
