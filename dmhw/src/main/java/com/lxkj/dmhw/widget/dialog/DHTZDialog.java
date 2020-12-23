package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.widget.editview.CleanableEditText;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.BBCUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 1 on 2016/11/1.
 * 到货通知弹框
 */
public class DHTZDialog {

    @BindView(R.id.et_phone_number)
    CleanableEditText etPhoneNumber;
    private Activity context;
    private Dialog overdialog;
    private Handler handler;

    private String notifyTel;
    public DHTZDialog(Activity context, Handler handler,String notifyTel) {
        this.context = context;
        this.handler = handler;
        this.notifyTel=notifyTel;
        initView();

    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_dhtz, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        overdialog.setContentView(view);
        if (!BBCUtil.isEmpty(notifyTel)&&notifyTel.length()>=11) {
            String str = notifyTel.substring(0, 3) + "****" + notifyTel.substring(7, notifyTel.length());
            etPhoneNumber.setText(str);
            try {
                etPhoneNumber.setSelection(str.length());//将光标移至文字末尾
            }catch (Exception e){

            }
        }
        etPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPhoneNumber.setText("");
            }
        });

        showDialog();
    }

    @OnClick(R.id.dialog_ok_btn)
    public void ok() {
        if (BBCUtil.isEmpty(etPhoneNumber.getText().toString().trim())){
            ToastUtil.show(context,"请输入手机号码");
            return;
        }
        String phone = etPhoneNumber.getText().toString().trim();
        if (phone.contains("*")){
            phone=notifyTel;
        }

        if (!BaseLangUtil.isMobile(phone)){
            ToastUtil.show(context,"请输入正确的手机号码");
            return;
        }

        if (handler != null) {
            Message msg=handler.obtainMessage();
            msg.what=5;
            msg.obj=phone;
            handler.sendMessage(msg);
        }
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
            p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.85
            win.setAttributes(p);

        } else {
            overdialog.show();
        }
    }

    @OnClick(R.id.dialog_cancel_btn)
    public void cancel() {
        cancelDialog();

    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }

}
