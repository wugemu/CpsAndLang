package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.SimpleDialog;


public class ShoperDialog extends SimpleDialog<String> {

    private ImageView dialogShoperImage;
    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public ShoperDialog(Context context, String data) {
        super(context, R.layout.dialog_shopper, data, true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        helper.setOnClickListener(R.id.dialog_shoper_cancel, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_shoper_cancel:
                hideDialog();
                break;
        }
    }

}
