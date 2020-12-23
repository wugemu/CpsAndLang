package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.ApplyActivity;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

/**
 * 申请代理弹窗
 * Created by Android on 2018/6/19.
 */

public class ApplyDialog extends SimpleDialog<Boolean> {

    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public ApplyDialog(Context context, Boolean data) {
        super(context, data ? R.layout.dialog_apply_success : R.layout.dialog_apply_failed, data, true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        helper.setOnClickListener(R.id.dialog_apply_btn, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_apply_btn:
                if (data) {
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshStatus"), true, 0);
                }
                hideDialog();
                ((ApplyActivity) context).isFinish();
                break;
        }
    }
}
