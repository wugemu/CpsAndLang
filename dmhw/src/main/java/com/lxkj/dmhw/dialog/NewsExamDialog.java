package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.NewsExamAdapter;
import com.lxkj.dmhw.bean.NewsExam;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

public class NewsExamDialog extends SimpleDialog<String> {

    private TextView exam_text;
    Context context;
    private OnSureClickListener mListener;
    /**
     * @param context  上下文
     * @param data     数据
     */
    public NewsExamDialog(Context context, String data) {
        super(context, R.layout.dialog_news_exam, data, true, false);
        this.context=context;
        exam_text.setText(data);
    }
    @Override
    protected void convert(ViewHolder helper) {
        exam_text= helper.getView(R.id.exam_text);
        helper.setOnClickListener(R.id.exam_cancel, this);
        helper.setOnClickListener(R.id.exam_sure, this);
        helper.setOnClickListener(R.id.layout_root1, this);
        helper.setOnClickListener(R.id.layout_root, this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_root:
            case R.id.exam_cancel:
                dismiss();
                break;
            case R.id.layout_root1:
                break;
            case R.id.exam_sure:
                if (mListener != null) {
                    mListener.onClick();
                }
                dismiss();
                break;
        }
    }
    public void setSureClickListener(OnSureClickListener  li) {
        mListener = li;
    }
    public  interface OnSureClickListener {
        void onClick();
    }
}
