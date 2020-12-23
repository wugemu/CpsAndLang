package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Alibc;
import com.lxkj.dmhw.defined.SimpleDialog;


public class BiJiaDialog extends SimpleDialog<Alibc.Tips> {
    private BiJiaDialog.OnBtnClickListener mListener;
    public void setOnClickListener(BiJiaDialog.OnBtnClickListener li) {
        mListener = li;
    }
    /**
     * 初始化
     */
    public BiJiaDialog(Context context, Alibc.Tips tips) {
        super(context, R.layout.dialog_bijia, tips, true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        TextView title_key= helper.getView(R.id.title_key);
        TextPaint tp=title_key.getPaint();
        tp.setFakeBoldText(true);
        helper.setText(R.id.title_key,data.getTitle());
        LinearLayout content_layout= helper.getView(R.id.content_layout);
        JSONArray array= JSON.parseArray(data.getData());
        if (array.size()>0){
        for (int i=0;i<array.size();i++) {
            View view=getLayoutInflater().inflate(R.layout.view_dialog, null);
            TextView txt=view.findViewById(R.id.title_txt);
            txt.setText(array.get(i).toString());
            content_layout.addView(view);
        }
        }

        helper.setOnClickListener(R.id.dialog_bijia_rule, this);
        helper.setOnClickListener(R.id.dialog_cancel, this);
        helper.setOnClickListener(R.id.dialog_confirm, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_cancel:
                mListener.onClick(0);
                hideDialog();
                break;
            case R.id.dialog_confirm:
                mListener.onClick(1);
                hideDialog();
                break;
            case R.id.dialog_bijia_rule:
                mListener.onClick(2);
                break;
        }
    }

    public  interface OnBtnClickListener {
        void onClick(int pos);
    }
}
