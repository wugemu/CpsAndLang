package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.myinterface.Confirm2OKI;
import com.lxkj.dmhw.utils.BBCUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonTipDialogNew {
    @BindView(R.id.TextTip)
    TextView TextTip;
    @BindView(R.id.tv_common_tip)
    TextView tv_common_tip;
    @BindView(R.id.tv_ok)
    TextView tvOk;


    private Activity context;
    private Dialog overdialog;
    private Confirm2OKI confirm2OKI;
    private String title;
    private String content;

    public CommonTipDialogNew(Activity context, String title, String content, Confirm2OKI confirm2OKI) {
        this.context = context;
        this.confirm2OKI = confirm2OKI;
        this.title = title;
        this.content = content;
        initView();
    }

    public void initView() {
        View view = View.inflate(context, R.layout.dialog_commontip_new, null);
        ButterKnife.bind(this, view);
        if (!BBCUtil.isEmpty(title)) {
            TextTip.setText(title);
        }
        if (!BBCUtil.isEmpty(content)) {
            tv_common_tip.setText(content);
        }
        overdialog = new Dialog(context, R.style.dialog_lhp2);
        overdialog.setContentView(view);
        showDialog();
    }

    public void showDialog() {
        if (overdialog != null) {
            overdialog.show();
            Window win = overdialog.getWindow();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
            win.getDecorView().setPadding(0, 0, 0, 0);
//            p.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度设置为包裹内容

            p.height = (int) (d.getHeight() * 0.6);
            p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.85
            win.setAttributes(p);
        }
    }


    public void setCenterContent() {
        if (tv_common_tip != null) {
            tv_common_tip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
    }

    public void setOkBtn(String okStr) {
        if (tvOk != null) {
            tvOk.setText(okStr);
        }
    }

    @OnClick(R.id.tv_ok)
    public void clickOk() {
        overdialog.cancel();
        if (confirm2OKI != null) {
            confirm2OKI.executeOk();
        }
    }

}
