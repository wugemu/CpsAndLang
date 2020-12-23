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
import android.widget.TextView;

import com.example.test.andlang.http.ExecutorServiceUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.myinterface.TimerI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerDialog {
    @BindView(R.id.tv_waitsecond)
    TextView tv_waitsecond;
    private Activity activity;
    private Dialog dialog;
    private int waitSecond;//等待秒数
    private TimerI timerI;
    public TimerDialog(Activity activity, int waitSecond, TimerI timerI){
        this.activity=activity;
        this.waitSecond=waitSecond;
        this.timerI=timerI;
        initView();
    }
    private void initView() {
        View overdiaView = View.inflate(activity, R.layout.dialog_timer, null);
        ButterKnife.bind(this, overdiaView);
        tv_waitsecond.setText(waitSecond+"");
        dialog = new Dialog(activity, R.style.dialog_lhp2);
        dialog.setContentView(overdiaView);
        dialog.setCancelable(false);
        showDialog();
        ExecutorServiceUtil.getInstence().execute(timeTask);
    }
    private void showDialog() {
        if (dialog != null) {
            dialog.show();
            Window win = dialog.getWindow();
            WindowManager m = activity.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
            win.getDecorView().setPadding(0, 0, 0, 0);
            p.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度设置为包裹内容
            p.width = (int)(d.getWidth()*0.7); // 宽度设置为屏幕的0.85
            win.setAttributes(p);
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_waitsecond.setText(waitSecond+"");
        }
    };

    private Runnable timeTask = new Runnable() {
        @Override
        public void run() {
            while (waitSecond>0){
                try {
                    waitSecond--;
                    handler.sendEmptyMessage(0);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cancelDialog();
            if(timerI!=null){
                timerI.timeOver();
            }

        }
    };

    public void cancelDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
