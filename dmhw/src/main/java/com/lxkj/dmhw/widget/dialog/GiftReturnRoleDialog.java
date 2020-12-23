package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.bean.self.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 1 on 2016/11/1.
 * 礼包返券说明弹框
 */
public class GiftReturnRoleDialog {

    @BindView(R.id.tv_tax)
    TextView tvTax;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Activity context;
    private Dialog overdialog;
    private Product gift;
    private GoodBean goodBean;

    public GiftReturnRoleDialog(Activity context, Product gift) {
        this.context = context;
        this.gift = gift;
        initView();

    }

    public GiftReturnRoleDialog(Activity context, GoodBean goodBean) {
        this.context = context;
        this.goodBean = goodBean;
        initView();

    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_gift_return_info, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        overdialog.setContentView(view);
        if (gift != null) {
            tvTax.setText(gift.getGiftDes());
            tvTitle.setText("返券说明");
        }
        if (goodBean!=null){
            tvTax.setText(goodBean.getReturnDesc());
            tvTitle.setText("返额说明");
        }

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
            p.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度设置为包裹内容
            p.width = ViewGroup.LayoutParams.MATCH_PARENT; // 宽度设置为屏幕的0.85
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(p);

        } else {
            overdialog.show();
        }
    }

    @OnClick(R.id.iv_close)
    public void cancel() {
        cancelDialog();

    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }

}
