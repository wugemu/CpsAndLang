package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

import java.util.ArrayList;

/**
 * 提现弹窗
 * Created by Administrator on 2017/12/15 0015.
 */

public class WithdrawalsDialog implements View.OnClickListener {

    private Activity activity;
    private Dialog dialog = null;
    private String name;
    private EditText
            dialogWithdrawalsAccount,// 支付宝账户
            dialogWithdrawalsName;// 支付宝姓名

    public Dialog getDialog() {
        return dialog;
    }

    public WithdrawalsDialog(Activity activity, String name) {
        this.activity = activity;
        this.name = name;
        init();
    }

    public void init() {
        View view = LinearLayout.inflate(activity, R.layout.dialog_withdrawals, null);
        view.findViewById(R.id.dialog_withdrawals_btn).setOnClickListener(this);
        dialogWithdrawalsAccount = (EditText) view.findViewById(R.id.dialog_withdrawals_account);
        dialogWithdrawalsName = (EditText) view.findViewById(R.id.dialog_withdrawals_name);
        dialog = new Dialog(activity, R.style.myTransparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏
            View decorView = dialog.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            dialog.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_withdrawals_btn:
                if (TextUtils.isEmpty(dialogWithdrawalsAccount.getText().toString())) {
                    Toast.makeText(activity, "账户不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(dialogWithdrawalsName.getText().toString())) {
                        Toast.makeText(activity, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<String> arrayList = new ArrayList<>();
                        arrayList.add(dialogWithdrawalsAccount.getText().toString());
                        arrayList.add(dialogWithdrawalsName.getText().toString());
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess(name), arrayList, 0);
                        dialog.dismiss();
                    }
                }
                break;
        }
    }
}
