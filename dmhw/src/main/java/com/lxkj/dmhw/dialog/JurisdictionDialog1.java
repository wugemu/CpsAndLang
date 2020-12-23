package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.WebPageNavigationActivity;
import com.lxkj.dmhw.defined.SimpleDialog;

/**
 * 权限提示弹窗
 */

public class JurisdictionDialog1 extends SimpleDialog<String> {

    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    private OnSettingClickListener mListener;
    public JurisdictionDialog1(Context context, String data) {
        super(context, R.layout.dialog_jurisdiction, data, true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        helper.setText(R.id.dialog_text, "请在设置-应用管理-" + context.getString(R.string.app_name) + "-权限中开启" + data + "权限，以正常使用" + context.getString(R.string.app_name) + "功能");
        helper.setOnClickListener(R.id.dialog_installed, this);
        helper.setOnClickListener(R.id.dialog_close, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_installed:
                if (mListener != null) {
                    mListener.onSettingClick();
                }
                hideDialog();
                break;
            case R.id.dialog_close:
                hideDialog();
                ((WebPageNavigationActivity) context).isFinish();
                break;
        }
    }

    public void setShareClickListener(OnSettingClickListener li) {
        mListener = li;
    }

    public  interface OnSettingClickListener {
        void onSettingClick();
    }
}
