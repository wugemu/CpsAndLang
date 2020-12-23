package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.myinterface.Confirm3OKI;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.utils.BBCUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 1 on 2016/11/1.
 * 确认弹框
 */
public class ConfirmDialog {
    @BindView(R.id.ll_cancel_btn)
    LinearLayout ll_cancel_btn;
    @BindView(R.id.dialog_cancel_btn)
    Button dialogCancelBtn;
    @BindView(R.id.dialog_ok_btn)
    Button dialogOkBtn;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.iv_close2)
    ImageView iv_close2;
    @BindView(R.id.ll_confirm)
    LinearLayout ll_confirm;
    @BindView(R.id.iv_jihuo_check)
    ImageView ivJihuoCheck;
    @BindView(R.id.tv_check_txt)
    TextView tvCheckTxt;
    @BindView(R.id.ll_jihuo)
    LinearLayout llJihuo;
    @BindView(R.id.ll_inbuy_tip)
    LinearLayout ll_inbuy_tip;
    @BindView(R.id.ll_inbuy_tip1)
    LinearLayout ll_inbuy_tip1;
    @BindView(R.id.tv_inbuy_tip1)
    TextView tv_inbuy_tip1;
    @BindView(R.id.ll_inbuy_tip2)
    LinearLayout ll_inbuy_tip2;
    @BindView(R.id.tv_inbuy_tip2)
    TextView tv_inbuy_tip2;

    private Activity context;
    private Dialog overdialog;
    private ConfirmOKI oki;
    private String title;
    private String btnOk;
    private String btnCancel;
    private boolean isOkNOClose;
    @BindView(R.id.TextTip)
    TextView tvTitle;
    @BindView(R.id.tv_TextTitle)
    TextView tv_TextTitle;
    @BindView(R.id.tv_TextTitle2)
    TextView tv_TextTitle2;//二级标题
    private boolean isVisCheck;//是否显示选择
    private String checkText;
    private boolean isCheck;//是否选中
    private Confirm3OKI oki3;
    public ConfirmDialog(Activity context, String title, boolean isVisCheck, String checkText, Confirm3OKI oki3) {
        this.context = context;
        this.oki3 = oki3;
        this.title = title;
        this.isVisCheck=isVisCheck;
        this.checkText=checkText;
        initView();

    }

    public ConfirmDialog(Activity context, String title, ConfirmOKI oki) {
        this.context = context;
        this.oki = oki;
        this.title = title;
        initView();

    }

    public ConfirmDialog(Activity context, String title, String btnOk, String btnCancel, ConfirmOKI oki) {
        this.context = context;
        this.title = title;
        this.btnOk = btnOk;
        this.btnCancel = btnCancel;
        this.oki = oki;
        initView();

    }

    public ConfirmDialog(Activity context, String title, String btnOk, String btnCancel, boolean isOkNOClose, ConfirmOKI oki) {
        this.context = context;
        this.title = title;
        this.btnOk = btnOk;
        this.btnCancel = btnCancel;
        this.oki = oki;
        this.isOkNOClose = isOkNOClose;
        initView();
    }

    public void setButtonBackground(int res) {
        dialogCancelBtn.setBackgroundResource(res);
    }

    public void setButtonTextColor(int color) {
        dialogCancelBtn.setTextColor(color);
    }


    private void initView() {
        View view = View.inflate(context, R.layout.dialog_confirm, null);
        ButterKnife.bind(this, view);
        if (isVisCheck){
            llJihuo.setVisibility(View.VISIBLE);
            tvCheckTxt.setText(checkText);
            isCheck=false;
            ivJihuoCheck.setImageResource(R.mipmap.cart_uncheck);
            ivJihuoCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isCheck=!isCheck;
                    if (isCheck){
                        ivJihuoCheck.setImageResource(R.mipmap.share_check_selected);
                    }else{
                        ivJihuoCheck.setImageResource(R.mipmap.share_check_unchecked);
                    }
                }
            });
        }

        if (isOkNOClose) {
            overdialog = new Dialog(context, R.style.dialog_lhp2);
            overdialog.setCancelable(false);
        } else {
            overdialog = new Dialog(context, R.style.dialog_lhp);
        }
        if (BBCUtil.isEmpty(title)) {
            title = "是否确认？";
        }
        if (!BBCUtil.isEmpty(btnOk)) {
            dialogOkBtn.setText(btnOk);
        }
        if (!BBCUtil.isEmpty(btnCancel)) {
            dialogCancelBtn.setText(btnCancel);
        }
        overdialog.setCanceledOnTouchOutside(false);
        tvTitle.setText(title);
        overdialog.setContentView(view);
        showDialog();
    }

    public void hiddenOkBtn() {
        dialogOkBtn.setVisibility(View.GONE);
    }


    public void hiddenCancelBtn() {
        ll_cancel_btn.setVisibility(View.GONE);
    }

    public void textLeft() {
        tvTitle.setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setTvTitleColor(int colorText){
        tvTitle.setTextColor(colorText);
    }
    public void setTvTitleSize(int textSize){
        tvTitle.setTextSize(textSize);
    }

    public void setDialogTitle(String title) {
        tv_TextTitle.setText(title);
        tv_TextTitle.setVisibility(View.VISIBLE);
    }
    public void setDialogTitle2(String title){
        tv_TextTitle2.setText(title);
        tv_TextTitle2.setVisibility(View.VISIBLE);
    }
    public void setDialogTitle2Left(){
        tv_TextTitle2.setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setTextScroll(int num){
        if(num>7) {
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.dp_100));
            params.setMargins((int)context.getResources().getDimension(R.dimen.design_15dp),(int)context.getResources().getDimension(R.dimen.design_15dp),(int)context.getResources().getDimension(R.dimen.design_15dp),(int)context.getResources().getDimension(R.dimen.design_20dp));
            tvTitle.setLayoutParams(params);
        }

        tvTitle.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
    public void setOkBtnRedStyle() {
        dialogOkBtn.setBackgroundResource(R.drawable.dialog_right_btn_bg_red);
    }

    public void showClose2(int imgRes){
        iv_close2.setImageResource(imgRes);
        iv_close2.setVisibility(View.VISIBLE);
    }
    public void showClose() {
        iv_close.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_confirm.getLayoutParams();
        layoutParams.topMargin = (int) context.getResources().getDimension(R.dimen.view_toview_two);
        layoutParams.rightMargin = (int) context.getResources().getDimension(R.dimen.view_toview_two);
        ll_confirm.setLayoutParams(layoutParams);
    }
    public void setInbuytip(String inbuytip1,String inbuytip2){
        ll_inbuy_tip.setVisibility(View.VISIBLE);
        if(!BBCUtil.isEmpty(inbuytip1)) {
            ll_inbuy_tip1.setVisibility(View.VISIBLE);
            tv_inbuy_tip1.setText(inbuytip1);
        }else {
            ll_inbuy_tip1.setVisibility(View.GONE);
        }
        if(!BBCUtil.isEmpty(inbuytip2)) {
            ll_inbuy_tip2.setVisibility(View.VISIBLE);
            tv_inbuy_tip2.setText(inbuytip2);
        }else {
            ll_inbuy_tip2.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.dialog_ok_btn)
    public void ok() {
        if (oki != null) {
            oki.executeOk();
        }
        if (!isOkNOClose) {
            cancelDialog();
        }
        if (oki3!=null){
            oki3.executeOk(isCheck);
        }
    }

    @OnClick(R.id.iv_close)
    public void close() {
        cancelDialog();
    }

    @OnClick(R.id.iv_close2)
    public void close2() {
        cancelDialog();
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
//            p.height = (int) (d.getHeight() * 0.4);
            p.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的0.85
            win.setAttributes(p);

        } else {
            overdialog.show();
        }
    }

    @OnClick(R.id.dialog_cancel_btn)
    public void cancel() {
        if (oki != null) {
            oki.executeCancel();
        }
        cancelDialog();

    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }

}
