package com.lxkj.dmhw.activity;

import android.content.Intent;
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
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.bean.WeChatUserInfo;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindingActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.binding_phone)
    EditText bindingPhone;
    @BindView(R.id.binding_code_btn_text)
    TextView bindingCodeBtnText;
    @BindView(R.id.binding_code_btn)
    LinearLayout bindingCodeBtn;
    @BindView(R.id.binding_code)
    EditText bindingCode;
    @BindView(R.id.binding_btn)
    LinearLayout bindingBtn;
    @BindView(R.id.wechat_name_layout)
    LinearLayout wechat_name_layout;
    @BindView(R.id.wechat_code_layout)
    LinearLayout wechat_code_layout;
    private WeChatUserInfo info;
    @BindView(R.id.binding_btn_tv)
    TextView binding_btn_tv;
    @BindView(R.id.register_agreement_layout)
    LinearLayout register_agreement_layout;

    private String invitationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        info = (WeChatUserInfo) getIntent().getSerializableExtra("wechat");
        invitationCode=getIntent().getStringExtra("invitation");
        bindingPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    wechat_name_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_selected)));
                }else{
                    wechat_name_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_unselect)));
                }
            }
        });
     bindingCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
             if (hasFocus){
                 wechat_code_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_selected)));
             }else{
                 wechat_code_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_unselect)));
             }
         }
     });

        bindingPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==11){//判断手机号码是否正确
                   //正则判断手机号码是否合法
                    if (Utils.isMobile(bindingPhone.getText().toString().trim())) {
                        if (bindingCode.getText().toString().trim().length()==6){
                            bindingBtn.setEnabled(true);
                            binding_btn_tv.setTextColor(getResources().getColor(R.color.white));
                            bindingBtn.setBackgroundColor(Color.parseColor("#000000"));
                        }
                    }else{
                        toast("请输入正确的手机号码");
                    }

                }else{
                    bindingBtn.setEnabled(false);
                    binding_btn_tv.setTextColor(getResources().getColor(R.color.white));
                    bindingBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_btn_style)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bindingCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==6){//判断手机号码是否正确
                    if (Utils.isMobile(bindingPhone.getText().toString().trim())) {
                        bindingBtn.setEnabled(true);
                        binding_btn_tv.setTextColor(getResources().getColor(R.color.white));
                        bindingBtn.setBackgroundColor(Color.parseColor("#000000"));
                    }
                }else{
                    bindingBtn.setEnabled(false);
                    binding_btn_tv.setTextColor(getResources().getColor(R.color.white));
                    bindingBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_btn_style)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bindingBtn.setEnabled(false);
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.BindingWeChatSuccess) {
            dismissDialog();
            UserInfo login = (UserInfo) message.obj;
            if (login.getIsbinding().equals("0")) {
                if (invitationCode.equals("")){
                    Variable.userPhone=bindingPhone.getText().toString();
                    startActivity(new Intent(this, InvitationActivity.class).putExtra("phone", bindingPhone.getText().toString()).putExtra("wechat", info));
                }else{
                    showDialog();
                    // 请求注册
                    paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    paramMap.put("extensionid",invitationCode);
                    paramMap.put("userphone", bindingPhone.getText().toString());
                    paramMap.put("userpwd", Utils.getMD5("123456"));
                    paramMap.put("wxuuid",  info.getUnionid());
                    paramMap.put("wxmc", info.getNickname());
                    paramMap.put("wxhead", info.getHeadimgurl());
                    NetworkRequest.getInstance().POST(handler, paramMap, "RegisterWeChat", HttpCommon.RegisterWeChat);
                }
            } else {
                // 储存用户信息
                DateStorage.setInformation(login);
                // 设置登录状态
                DateStorage.setLoginStatus(true);
                // 通知登录成功
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
                ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
                // 关闭页面
                isFinish();
            }

        }

        if (message.what == LogicActions.RegisterWeChatSuccess) {
            // 隐藏弹窗
            dismissDialog();
            // 储存用户信息
            UserInfo login = (UserInfo) message.obj;
            DateStorage.setInformation(login);
            // 设置登录状态
            DateStorage.setLoginStatus(true);
            // 通知登录成功
            Variable.AuthShowStatus=true;//不让授权登录窗口显示
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
            //注册成功，奖励金币弹框
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RegisterStatus"), true, 0);
            // 关闭登录页面
            ActivityManagerDefine.getInstance().popClass(SignInActivity.class);
            ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
            // 关闭页面
            isFinish();
        }
    }

    @OnClick({R.id.back, R.id.binding_code_btn, R.id.binding_btn,R.id.register_agreement_user,R.id.register_agreement_secret})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.binding_code_btn:
                // 判断手机号是否为空
                if (TextUtils.isEmpty(bindingPhone.getText().toString())) {
                    toast("手机号不能为空");
                } else {
                    // 判断手机号格式是否正确
                    if (Utils.isMobile(bindingPhone.getText().toString())) {
                        // 隐藏输入法
                        Utils.hideSoftInput(this, null);
                        // 倒计时
                        countDown();
                        // 请求获取验证码
                        paramMap.clear();
                        paramMap.put("userphone", bindingPhone.getText().toString());
                        paramMap.put("extensionid", getIntent().getExtras().getString("invitation"));
                        paramMap.put("reqsource", "00");
                        NetworkRequest.getInstance().POST(handler, paramMap, "RegisterCode", HttpCommon.RegisterCode);
                    } else {
                        toast("请输入正确的手机号码");
                    }
                }
                break;
            case R.id.binding_btn:
                // 判断手机号是否为空
                if (TextUtils.isEmpty(bindingPhone.getText().toString())) {
                    toast("手机号不能为空");
                } else {
                    // 判断手机号格式是否正确
                    if (!Utils.isMobile(bindingPhone.getText().toString())) {
                        toast("请输入正确的手机号码");
                    } else {
                        // 判断验证码是否为空
                        if (TextUtils.isEmpty(bindingCode.getText().toString())) {
                            toast("验证码不能为空");
                        } else {
                            // 判断验证码格式
                            if (bindingCode.getText().length() < 6) {
                                toast("验证码格式不正确，请重新填写");
                            } else {
                                showDialog();
                                paramMap.clear();
                                paramMap.put("userid", login.getUserid());
                                paramMap.put("userphone", bindingPhone.getText().toString());
                                paramMap.put("smscode", bindingCode.getText().toString());
                                paramMap.put("wxuuid", info.getUnionid());
                                paramMap.put("wxmc", info.getNickname());
                                paramMap.put("wxhead", info.getHeadimgurl());
                                NetworkRequest.getInstance().POST(handler, paramMap, "BindingWeChat", HttpCommon.BindingWeChat);
                            }
                        }
                    }
                }
                break;
            case R.id.register_agreement_user:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(Variable.webUrl, Variable.protocl_Agreement_User);
                startActivity(intent);
                break;
            case R.id.register_agreement_secret:
                Intent intent1 = new Intent(this, WebViewActivity.class);
                intent1.putExtra(Variable.webUrl, Variable.protocl_Agreement_Secret);
                startActivity(intent1);
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
                // 设置获取验证码按钮不可点击
                if (bindingCodeBtnText==null){
                    this.cancel();
                    return;
                }
                bindingCodeBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_count_style)));
                bindingCodeBtn.setEnabled(false);
                bindingCodeBtnText.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                if (bindingCodeBtnText==null){
                    this.cancel();
                    return;
                }
                bindingCodeBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_code_style)));
                bindingCodeBtnText.setText("重新发送");
                bindingCodeBtn.setEnabled(true);
            }
        }.start();
    }
}
