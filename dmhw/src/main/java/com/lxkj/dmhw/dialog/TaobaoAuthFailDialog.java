package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.SimpleDialog;


public class TaobaoAuthFailDialog extends SimpleDialog<String> {

    private TextView tlogin_failed_txt;
    /**
     * 授权失败弹窗
     * @param context  上下文
     * @param data     数据
     */
    public TaobaoAuthFailDialog(Context context, String data) {
        super(context, R.layout.dialog_taobao_auth_failed, data, true, false);
        this.context=context;

    }

    @Override
    protected void convert(ViewHolder helper) {
        tlogin_failed_txt= helper.getView(R.id.tlogin_failed_txt);
        tlogin_failed_txt.setText(data);
        helper.setOnClickListener(R.id.tlogin_failed_confirm, this);
        helper.setOnClickListener(R.id.layout_root, this);
        helper.setOnClickListener(R.id.layout_root01, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_root:
                dismiss();
                break;
            case R.id.layout_root01:
                break;
            case R.id.tlogin_failed_confirm:
                dismiss();
                break;
        }
    }


}
