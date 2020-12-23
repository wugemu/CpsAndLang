package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lxkj.dmhw.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 1 on 2017/5/16.
 */

public class StockTipPopupwindow extends PopupWindow {

    @BindView(R.id.tv_tip)
    TextView tvTip;
    private View contentView;
    private Activity activity;

    public StockTipPopupwindow(Activity context, String text) {
        this.activity = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.pop_stock_tip, null);
        ButterKnife.bind(this, contentView);
        tvTip.setText(text);
        this.setContentView(contentView);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        this.update();

    }
    public void updateView(View parent){
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        update((location[0] + parent.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight-25,popupWidth,popupHeight);
    }

    public void showEditPopup(View parent) {
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();

        if (!this.isShowing()) {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            showAtLocation(parent, Gravity.NO_GRAVITY, (location[0] + parent.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight-25);
        }
    }


}
