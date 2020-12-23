package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.push_switch)
    Switch pushSwitch;
    @BindView(R.id.push_one_text)
    TextView pushOneText;
    @BindView(R.id.push_two_text)
    TextView pushTwoText;
    @BindView(R.id.push_three_text)
    TextView pushThreeText;
    @BindView(R.id.push_four_text)
    TextView pushFourText;
    @BindView(R.id.push_five_text)
    TextView pushFiveText;
    private static final int REQUEST_NOTIFICATION = 101;
    private String  content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        setData();
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {

    }

    private void setData() {
//        String content;
        if (Utils.isNotification(this)) {
            pushSwitch.setChecked(true);
            DateStorage.setPush("已开启");
            content = "已开启";
        } else {
            pushSwitch.setChecked(false);
            DateStorage.setPush("未开启");
            content = "未开启";
        }
        pushOneText.setText(content);
        pushTwoText.setText(content);
        pushThreeText.setText(content);
        pushFourText.setText(content);
        pushFiveText.setText(content);
    }

    @OnClick({R.id.back, R.id.push_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.push_switch:
                setData();
                Utils.jumpNotification(this, REQUEST_NOTIFICATION);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NOTIFICATION) {
            setData();
        }
    }
}
