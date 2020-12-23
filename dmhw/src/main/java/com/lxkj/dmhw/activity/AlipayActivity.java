package com.lxkj.dmhw.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.Withdrawals;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlipayActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.alipay_name)
    EditText alipayName;
    @BindView(R.id.alipay_account)
    EditText alipayAccount;
    @BindView(R.id.alipay_phone)
    TextView alipayPhone;
    @BindView(R.id.alipay_code)
    EditText alipayCode;
    @BindView(R.id.alipay_code_btn_text)
    TextView alipayCodeBtnText;
    @BindView(R.id.alipay_code_btn)
    LinearLayout alipayCodeBtn;
    @BindView(R.id.alipay_btn)
    LinearLayout alipayBtn;
    @BindView(R.id.alipay_name_close_btn)
    LinearLayout alipayNameCloseBtn;
    @BindView(R.id.alipay_account_close_btn)
    LinearLayout alipayAccountCloseBtn;
    private Withdrawals withdrawals;
    @BindView(R.id.alipay_btn_txt)
    TextView alipay_btn_txt;

    @BindView(R.id.nohasalipay)
    LinearLayout nohasalipay;
    @BindView(R.id.alipay_title)
    TextView alipay_title;
    @BindView(R.id.alipay_explain)
    TextView alipay_explain;
    private boolean ishasAlipay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        alipayBtn.setEnabled(false);
        alipayName.addTextChangedListener(nameWatcher);
        alipayAccount.addTextChangedListener(accountWatcher);
        alipayCode.addTextChangedListener(codeWatcher);
        ishasAlipay=getIntent().getBooleanExtra("isHasAlipay",false);

        if (ishasAlipay)
        {
            //有支付宝
            alipay_title.setText("修改支付宝账号");
            alipay_btn_txt.setText("确认修改");
            alipay_explain.setVisibility(View.GONE);
            // 获取提现信息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawals", HttpCommon.Withdrawals);

        }else{
            //没有支付宝
            alipay_title.setText("设置支付宝账号");
            alipay_btn_txt.setText("确认设置");
            alipayPhone.setText(Utils.phoneEncryption(login.getUserphone()));
            alipay_explain.setVisibility(View.GONE);
        }
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        dismissDialog();
        // 获取验证码成功回调
        if (message.what == LogicActions.RegisterCodeSuccess) {
            toast(message.obj.toString());
        }
        if (message.what == LogicActions.SetAlipaySuccess) {
            toast(message.obj + "");
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("AmendAliPay"), "", 0);
            isFinish();
        }
        if (message.what == LogicActions.WithdrawalsSuccess) {
            withdrawals = (Withdrawals) message.obj;
            alipayName.setText(withdrawals.getAlipayname());
            alipayAccount.setText(withdrawals.getAlipayacount());
            alipayName.setSelection(alipayName.getText().length());
            alipayPhone.setText(Utils.phoneEncryption(login.getUserphone()));
        }
        if (message.what == LogicActions.BindingAlipaySuccess) {
            toast(message.obj + "");
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("AmendAliPay"), "", 0);
            isFinish();
        }
    }

    @OnClick({R.id.back, R.id.alipay_code_btn, R.id.alipay_btn, R.id.alipay_name_close_btn, R.id.alipay_account_close_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:// 返回
                isFinish();
                break;
            case R.id.alipay_code_btn:// 获取验证码
                // 隐藏输入法
                Utils.hideSoftInput(this, null);
                // 倒计时
                countDown();
                // 请求获取验证码
                paramMap.clear();
                paramMap.put("userphone", login.getUserphone());
                paramMap.put("reqsource", "00");
                NetworkRequest.getInstance().POST(handler, paramMap, "RegisterCode", HttpCommon.RegisterCode);
                showDialog();
                break;
            case R.id.alipay_btn:// 确认修改
                if (ishasAlipay){
                if (TextUtils.isEmpty(alipayName.getText().toString())) {
                    toast("用户姓名不能为空");
                } else {
                    if (TextUtils.isEmpty(alipayAccount.getText().toString())) {
                        toast("支付宝账户不能为空");
                    } else {
                        if (TextUtils.isEmpty(alipayCode.getText().toString())) {
                            toast("验证码不能为空");
                        } else {
                            paramMap.clear();
                            paramMap.put("userid", login.getUserid());
                            paramMap.put("alipayacount", alipayAccount.getText().toString());
                            paramMap.put("alipayname", alipayName.getText().toString());
                            paramMap.put("userphone", login.getUserphone());
                            paramMap.put("smscode", alipayCode.getText().toString());
                            NetworkRequest.getInstance().POST(handler, paramMap, "SetAlipay", HttpCommon.SetAlipay);
                            showDialog();
                        }
                    }
                }
                }else{
//                    paramMap.clear();
//                    paramMap.put("userid", login.getUserid());
//                    paramMap.put("type", "0");
//                    paramMap.put("alipayacount", alipayAccount.getText().toString());
//                    paramMap.put("alipayname", alipayName.getText().toString());
//                    NetworkRequest.getInstance().POST(handler, paramMap, "BindingAlipay", HttpCommon.BindingAlipay);
                    paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    paramMap.put("alipayacount", alipayAccount.getText().toString());
                    paramMap.put("alipayname", alipayName.getText().toString());
                    paramMap.put("userphone", login.getUserphone());
                    paramMap.put("smscode", alipayCode.getText().toString());
                    NetworkRequest.getInstance().POST(handler, paramMap, "SetAlipay", HttpCommon.SetAlipay);
                    showDialog();
                }

                break;
            case R.id.alipay_name_close_btn:
                alipayName.setText("");
                break;
            case R.id.alipay_account_close_btn:
                alipayAccount.setText("");
                break;
        }
    }

    /**
     * 倒计时
     */
    private void countDown() {
        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    // 设置获取验证码按钮不可点击
                    alipayCodeBtn.setEnabled(false);
                    alipayCodeBtnText.setText(millisUntilFinished / 1000 + "s");
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }

            @Override
            public void onFinish() {
                try {
                    alipayCodeBtnText.setText("获取验证码");
                    alipayCodeBtn.setEnabled(true);
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }
        }.start();
    }

    TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // 输入前的监听
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            listenEdittext();
            // 输入的内容变化的监听
            if (s.toString().equals("")) {
                alipayNameCloseBtn.setVisibility(View.GONE);
            } else {
                alipayNameCloseBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // 输入后的监听
        }
    };

    TextWatcher accountWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // 输入前的监听
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            listenEdittext();
            // 输入的内容变化的监听
            if (s.toString().equals("")) {
                alipayAccountCloseBtn.setVisibility(View.GONE);
            } else {
                alipayAccountCloseBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // 输入后的监听
        }
    };
    TextWatcher codeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // 输入前的监听
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (alipayName.getText().length()>0&&alipayAccount.getText().length()>0&&alipayCode.getText().length()==6){
                alipayBtn.setBackgroundResource(R.drawable.logout_btn_bg);
                alipay_btn_txt.setTextColor(Color.parseColor("#ffffff"));
                alipayBtn.setEnabled(true);
            }else{
                alipayBtn.setBackgroundResource(R.drawable.nocheck_btn_bg);
                alipay_btn_txt.setTextColor(Color.parseColor("#999999"));
                alipayBtn.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // 输入后的监听
        }
    };


    private  void  listenEdittext(){
        if (ishasAlipay) {
            if (alipayName.getText().length() > 0 && alipayAccount.getText().length() > 0 && alipayCode.getText().length() == 6) {
                alipayBtn.setBackgroundResource(R.drawable.logout_btn_bg);
                alipay_btn_txt.setTextColor(Color.parseColor("#ffffff"));
                alipayBtn.setEnabled(true);
            } else {
                alipayBtn.setBackgroundResource(R.drawable.nocheck_btn_bg);
                alipay_btn_txt.setTextColor(Color.parseColor("#999999"));
                alipayBtn.setEnabled(false);
            }
        }else{
            if (alipayName.getText().length() > 0 && alipayAccount.getText().length() > 0) {
                alipayBtn.setBackgroundResource(R.drawable.logout_btn_bg);
                alipay_btn_txt.setTextColor(Color.parseColor("#ffffff"));
                alipayBtn.setEnabled(true);
            } else {
                alipayBtn.setBackgroundResource(R.drawable.nocheck_btn_bg);
                alipay_btn_txt.setTextColor(Color.parseColor("#999999"));
                alipayBtn.setEnabled(false);
            }
        }
    }
}
