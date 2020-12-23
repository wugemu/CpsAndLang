package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.SimpleDialog;

/**
 * 加载提示
 * Created by Zyhant on 2017/7/16.
 */

public class ProgressDialog extends SimpleDialog<String> {

    private ImageView loadingImg;
    private TextView loadingtxt;
    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public ProgressDialog(Context context, String data) {
        super(context, R.layout.dialog_progress, data, false, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        loadingImg = helper.getView(R.id.gifimage);
        helper.setText(R.id.loadingtxt,"请稍候...");
        try{
            loadingImg.setBackgroundResource(R.drawable.loading);
            AnimationDrawable animaition = (AnimationDrawable)loadingImg.getBackground();
            animaition.start();
        }catch (Exception e){
            Log.e("0.0",e.toString());
        }
    }

    @Override
    public void onClick(View v) {

    }
}
