package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.test.andlang.http.ExecutorServiceUtil;
import com.example.test.andlang.widget.editview.CleanableEditText;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.myinterface.SalePayI;
import com.lxkj.dmhw.utils.BBCUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SalePayDialog {
    @BindView(R.id.ce_pay_mobile)
    CleanableEditText ce_pay_mobile;
    @BindView(R.id.ce_pay_code)
    CleanableEditText ce_pay_code;
    @BindView(R.id.btn_pay_send_code)
    Button btn_pay_send_code;
    @BindView(R.id.btn_safephone)
    Button btn_safephone;

    private Activity context;
    private Dialog overdialog;
    private String phone;
    private SalePayI salePayI;
    public SalePayDialog(Activity context,String phone,SalePayI salePayI){
        this.context=context;
        this.phone=phone;
        this.salePayI=salePayI;
        initView();
    }
    public void initView(){
        View view = View.inflate(context, R.layout.dialog_salepay, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp2);
        overdialog.setContentView(view);
        ce_pay_mobile.setText(phone);
        ce_pay_mobile.hiddenRightDraw();
        ce_pay_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeButtonState();
            }
        });

        showDialog();
    }

    public void changeButtonState(){
        if(!BBCUtil.isEmpty(ce_pay_code.getText().toString().trim())){
            btn_safephone.setBackgroundResource(R.drawable.bg_rect_new);
            btn_safephone.setEnabled(true);
        }else {
            btn_safephone.setBackgroundResource(R.drawable.bg_rect_new3);
            btn_safephone.setEnabled(false);
        }
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
            p.width = d.getWidth();
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(p);

        }else {
            overdialog.show();
        }
    }

    @OnClick(R.id.btn_safephone)
    public void commit(){
        if(salePayI!=null){
            String captcha=ce_pay_code.getText().toString().trim();
            salePayI.commit(captcha);
        }
    }

    @OnClick(R.id.btn_pay_send_code)
    public void sendCode(){
        if(salePayI!=null){
            salePayI.sendCode();
        }
    }

    public void startDownTime(){
        //开始倒计时
        ExecutorServiceUtil.getInstence().execute(timeTask);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (btn_pay_send_code != null) {
                        if (msg.arg1 > 0) {
                            btn_pay_send_code.setTextColor(context.getResources().getColor(R.color.colorBlackHint));
                            btn_pay_send_code.setBackgroundResource(R.drawable.bg_rect_stroke_new3);
                            btn_pay_send_code.setClickable(false);
                            btn_pay_send_code.setText(msg.arg1 + "S");
                        } else {
                            btn_pay_send_code.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                            btn_pay_send_code.setBackgroundResource(R.drawable.bg_rect_stroke_new);
                            btn_pay_send_code.setText("重新发送");
                            btn_pay_send_code.setClickable(true);
                        }
                    }
                    break;

            }
        }
    };

    private Runnable timeTask = new Runnable() {
        @Override
        public void run() {
            for (int i = 60; i >= 0; i--) {
                try {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @OnClick(R.id.iv_coupon_close)
    public void close(){
        overdialog.cancel();
        if(salePayI!=null){
            salePayI.clickClose();
        }
    }

    public void cancelDialog(){
        if(overdialog!=null) {
            overdialog.cancel();
        }
    }
}
