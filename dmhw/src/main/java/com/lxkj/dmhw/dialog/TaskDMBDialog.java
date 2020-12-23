package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.BaseDialogNew;
import com.lxkj.dmhw.R;

public class TaskDMBDialog extends BaseDialogNew {

    private TextView title,txt_001;
//    private TextView  txt_002;
    private LinearLayout dialog_save_cancel;



    public TaskDMBDialog(Activity activity) {
        super(activity);
        title = findView(R.id.title);
        txt_001 = findView(R.id.txt_001);
//        txt_002 = findView(R.id.txt_002);
        dialog_save_cancel = findView(R.id.dialog_save_cancel);
        dialog_save_cancel.setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_task_dmb, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_save_cancel:
                dialog.dismiss();
                break;

        }
    }

    public void showDialog(String title_head, String txt1) {
        title.setText(title_head);
        txt_001.setText(txt1);
//        txt_002.setText(txt2);
        dialog.show();
    }
}
