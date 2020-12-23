package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.SimpleDialog;

public class OneKeyShareDialog extends SimpleDialog<String> {

    Context context;
    private OnOneKeyClickListener mListener;
    String content="";
    /**
     * @param context  上下文
     * @param data     数据
     */
    public OneKeyShareDialog(Context context, String data) {
        super(context, R.layout.dialog_onekey, data, true, false);
        this.context=context;
        this.content=data;
    }
    @Override
    protected void convert(ViewHolder helper) {
        helper.setOnClickListener(R.id.dialog_tips_cancel, this);
        helper.setOnClickListener(R.id.dialog_tips_confirm, this);
        helper.setText(R.id.dialog_tips_content,data);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_tips_cancel:
               hideDialog();
                break;
            case R.id.dialog_tips_confirm:
                if (mListener != null) {
                    mListener.onClick();
                }
                hideDialog();
                break;
        }
    }
    public void setOneKeyClickListener(OnOneKeyClickListener  li) {
        mListener = li;
    }
    public  interface OnOneKeyClickListener {
        void onClick();
    }
}
