package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.lxkj.dmhw.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 1 on 2017/5/16.
 */

public class ProductEditPopupwindow extends PopupWindow {

    @BindView(R.id.tv_twbj)
    TextView tvTwbj;
    private View contentView;
    private Handler handler;
    private Activity activity;

    public ProductEditPopupwindow(Activity context, Handler handler) {
        this.handler = handler;
        this.activity = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.pop_product, null);
        ButterKnife.bind(this, contentView);
        this.setContentView(contentView);
        this.setWidth((int)activity.getResources().getDimension(R.dimen.dp_68));
        this.setHeight( (int)activity.getResources().getDimension(R.dimen.dp_31));
        this.setFocusable(false);
        this.setOutsideTouchable(true);
        this.update();
    }

    public void showEditPopup(View parent) {
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        if (!this.isShowing()) {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            showAtLocation(parent, Gravity.NO_GRAVITY, (location[0] + parent.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight-(int)activity.getResources().getDimension(R.dimen.dp_12));
        } else {
            this.dismiss();
        }
    }


    @OnClick({R.id.tv_twbj})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_twbj://进入图文编辑页面
                handler.sendEmptyMessage(2);
                dismiss();
                break;

        }
    }
}
