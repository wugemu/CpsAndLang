package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.andlang.util.BaseLangUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.myinterface.ConfirmOKI;

/**
 * Created by 1 on 2016/11/1.
 * 确认弹框
 */
public class Confirm3Dialog {


    private TextView tvTextTitle;
    private Button dialogOkBtn;
    private Activity context;
    private Dialog overdialog;
    private ConfirmOKI oki;
    private String title;

    public Confirm3Dialog(Activity context, String title, ConfirmOKI oki) {
        this.context = context;
        this.oki = oki;
        this.title = title;
        initView();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_confirm3, null);
        tvTextTitle=view.findViewById(R.id.tv_TextTitle);
        dialogOkBtn=view.findViewById(R.id.dialog_ok_btn);


        overdialog = new Dialog(context, R.style.dialog_lhp);
        if (BaseLangUtil.isEmpty(title)) {
            title = "是否需要关闭直播?";
        }

        dialogOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oki!=null){
                    oki.executeOk();
                }
                cancelDialog();
            }
        });

        overdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (oki!=null){
                    oki.executeCancel();
                }
            }
        });


        tvTextTitle.setText(title);
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
            p.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度设置为包裹内容
//            p.height = (int) (d.getHeight() * 0.4);
            p.width = (int) (d.getWidth() * 0.85); // 宽度设置为屏幕的0.85
            win.setAttributes(p);

        } else {
            overdialog.show();
        }
    }



    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }


}
