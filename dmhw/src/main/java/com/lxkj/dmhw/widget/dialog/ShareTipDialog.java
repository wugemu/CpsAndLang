package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.BBCUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareTipDialog {
    private Activity context;
    private Dialog overdialog;
    //右上角商品列表分享
    public ShareTipDialog(Activity context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        initView();
    }

    private void initView() {
        View overdiaView = View.inflate(context, R.layout.dialog_share_tip,
                null);
        ButterKnife.bind(this, overdiaView);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        overdialog.setContentView(overdiaView);

        showDialog();
    }

    public void showDialog() {
        if (overdialog != null) {
            overdialog.show();
            Window win = overdialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(lp);

        }

    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }


    @OnClick({R.id.iv_close,R.id.ll_open_wx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                cancelDialog();
                break;
            case R.id.ll_open_wx:
                //打开微信
                BBCUtil.openWX(context);
                cancelDialog();
                break;
        }
    }
}
