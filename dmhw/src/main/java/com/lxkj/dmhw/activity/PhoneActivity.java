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

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.phone_tips_text)
    TextView phoneTipsText;
    @BindView(R.id.phone_edit_text)
    EditText phoneEditText;
    @BindView(R.id.phone_code_edit)
    EditText phoneCodeEdit;
    @BindView(R.id.phone_code_btn_text)
    TextView phoneCodeBtnText;
    @BindView(R.id.phone_code_btn)
    LinearLayout phoneCodeBtn;
    @BindView(R.id.phone_btn_text)
    TextView phoneBtnText;
    @BindView(R.id.phone_btn)
    LinearLayout phoneBtn;
    private CountDownTimer count;
    private boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        phoneEditText.setText(Utils.phoneEncryption(login.getUserphone()));


        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             if (phoneEditText.getText().toString().length()>0&&phoneCodeEdit.getText().toString().length()==6) {

                 phoneBtn.setBackgroundResource(R.drawable.logout_btn_bg);
                 phoneBtnText.setTextColor(Color.parseColor("#ffffff"));
                 phoneBtn.setEnabled(true);

             }else{
                 phoneBtn.setBackgroundResource(R.drawable.nocheck_btn_bg);
                 phoneBtnText.setTextColor(Color.parseColor("#999999"));
                 phoneBtn.setEnabled(false);
             }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phoneCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phoneEditText.getText().toString().length()>0&&phoneCodeEdit.getText().toString().length()==6) {
                    phoneBtn.setBackgroundResource(R.drawable.logout_btn_bg);
                    phoneBtnText.setTextColor(Color.parseColor("#ffffff"));
                    phoneBtn.setEnabled(true);
                }else{
                    phoneBtn.setBackgroundResource(R.drawable.nocheck_btn_bg);
                    phoneBtnText.setTextColor(Color.parseColor("#999999"));
                    phoneBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        if (message.what == LogicActions.RegisterCodeSuccess) {
            toast(message.obj.toString());
        }
        if (message.what == LogicActions.VerificationOldPhoneSuccess) {
            phoneTipsText.setText("新手机号");
            phoneEditText.setText("");
            phoneEditText.setEnabled(true);
            phoneCodeEdit.setText("");
            phoneCodeBtnText.setText("获取验证码");
            phoneCodeBtn.setEnabled(true);
            phoneBtnText.setText("绑定新手机");
            count.cancel();
            count = null;
            isCheck = true;
        }
        if (message.what == LogicActions.ModificationPhoneSuccess) {
            toast(message.obj + "");
            login = DateStorage.getInformation();
            login.setUserphone(phoneCodeEdit.getText().toString());
            DateStorage.setInformation(login);
            login = DateStorage.getInformation();
            isFinish();
        }
    }

    @OnClick({R.id.back, R.id.phone_code_btn, R.id.phone_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.phone_code_btn:// 验证码
                if (TextUtils.isEmpty(phoneEditText.getText().toString())) {
                    toast("手机号不能为空");
                } else {
                    if (isCheck) {
                        if (!Utils.isMobile(phoneEditText.getText().toString())) {
                            toast("手机号格式不正确，请重新填写");
                        } else {
                            // 隐藏输入法
                            Utils.hideSoftInput(this, null);
                            // 倒计时
                            countDown();
                            // 请求获取验证码
                            paramMap.clear();
                            paramMap.put("userphone", phoneEditText.getText().toString());
                            paramMap.put("reqsource", "00");
                            NetworkRequest.getInstance().POST(handler, paramMap, "RegisterCode", HttpCommon.RegisterCode);
                            showDialog();
                        }
                    } else {
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
                    }
                }
                break;
            case R.id.phone_btn:// 确认
                if (isCheck) {
                    if (TextUtils.isEmpty(phoneEditText.getText().toString())) {
                        toast("手机号不能为空");
                    } else if (!Utils.isMobile(phoneEditText.getText().toString())){
                        toast("手机号格式不正确，请重新填写");
                    } else if (TextUtils.isEmpty(phoneCodeEdit.getText().toString())) {
                        toast("验证码不能为空");
                    } else if (phoneCodeEdit.getText().length() < 6) {
                        toast("验证码格式不正确，请重新填写");
                    } else {
                        paramMap.clear();
                        paramMap.put("olduserphone", login.getUserphone());
                        paramMap.put("userphone", phoneEditText.getText().toString());
                        paramMap.put("smscode", phoneCodeEdit.getText().toString());
                        NetworkRequest.getInstance().POST(handler, paramMap, "ModificationPhone", HttpCommon.ModificationPhone);
                        showDialog();
                    }
                } else {
                    if (TextUtils.isEmpty(phoneCodeEdit.getText().toString())) {
                        toast("验证码不能为空");
                    } else {
                        // 验证旧手机号
                        paramMap.clear();
                        paramMap.put("userphone", login.getUserphone());
                        paramMap.put("smscode", phoneCodeEdit.getText().toString());
                        NetworkRequest.getInstance().POST(handler, paramMap, "VerificationOldPhone", HttpCommon.VerificationOldPhone);
                        showDialog();
                    }
                }
                break;
        }
    }

    /**
     * 倒计时
     */
    private void countDown() {
        count = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    // 设置获取验证码按钮不可点击
                    phoneCodeBtn.setEnabled(false);
                    phoneCodeBtnText.setText(millisUntilFinished / 1000 + "s");
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }

            @Override
            public void onFinish() {
                try {
                    phoneCodeBtnText.setText("获取验证码");
                    phoneCodeBtn.setEnabled(true);
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }
        }.start();
    }
}
