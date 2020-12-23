package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.SimpleDialog;

/**
 * 跳转支付宝加载提示
 * Created by Android on 2018/9/26.
 */

public class AlipayDialog extends SimpleDialog<String> {

    /**
     * 初始化
     * @param context  上下文
     */
    public AlipayDialog(Context context) {
        super(context, R.layout.dialog_alipay, "", true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {

    }

    @Override
    public void onClick(View v) {

    }
}
