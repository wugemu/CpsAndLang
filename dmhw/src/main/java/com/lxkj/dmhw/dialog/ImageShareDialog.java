package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

/**
 * 图片分享设置
 * Created by Administrator on 2017/12/22 0022.
 */

public class ImageShareDialog extends SimpleDialog<String> {

    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public ImageShareDialog(Context context, String data) {
        super(context, R.layout.dialog_image_share, data, true, false);
    }

    @Override
    protected void convert(ViewHolder helper) {
        helper.setOnClickListener(R.id.dialog_close, this);
        helper.setOnClickListener(R.id.dialog_image_share_yes, this);
        helper.setOnClickListener(R.id.dialog_image_share_no, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                hideDialog();
                break;
            case R.id.dialog_image_share_yes:
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess(data), "带二维码", 0);
                hideDialog();
                break;
            case R.id.dialog_image_share_no:
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess(data), "无二维码", 0);
                hideDialog();
                break;
        }
    }
}
