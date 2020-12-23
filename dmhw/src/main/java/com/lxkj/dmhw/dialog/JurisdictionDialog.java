package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.StartActivity340;
import com.lxkj.dmhw.defined.SimpleDialog;

/**
 * 权限提示弹窗
 * Created by Zyhant on 2017/2/14.
 */

public class JurisdictionDialog extends SimpleDialog<String> {

    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public JurisdictionDialog(Context context, String data) {
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
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideDialog();
                break;
            case R.id.dialog_close:
                hideDialog();
                break;
        }
    }
}
