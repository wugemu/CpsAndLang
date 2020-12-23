package com.lxkj.dmhw.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.dialog.WeixinLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginCodeFragment extends BaseFragment {

    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_code)
    EditText loginCode;
    @BindView(R.id.login_access)
    LinearLayout loginAccess;
    @BindView(R.id.login_btn)
    LinearLayout loginBtn;
    @BindView(R.id.login_access_text)
    TextView loginAccessText;
    @BindView(R.id.binding_btn_tv)
    TextView binding_btn_tv;

    @BindView(R.id.wechat_name_layout)
    LinearLayout wechat_name_layout;
    @BindView(R.id.view_phone_line)
    View view_phone_line;
    @BindView(R.id.wechat_code_layout)
    LinearLayout wechat_code_layout;
    @BindView(R.id.view_code_line)
    View view_code_line;


    private IWXAPI api;
    WeixinLoginDialog wxdialog;
    public static LoginCodeFragment getInstance() {
        LoginCodeFragment fragment = new LoginCodeFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_code, null);
        ButterKnife.bind(this, view);
        api = Utils.weChat(getActivity(), false);
        Variable.weChatCheck = true;
        loginPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (view_phone_line==null){
                    return;
                }
                if (hasFocus){
                    view_phone_line.setBackgroundColor(getResources().getColor(R.color.black));
                }else{
                    view_phone_line.setBackgroundColor(Color.parseColor("#F3F4F4"));
                }
            }
        });
        loginCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (view_code_line==null){
                  return;
                }
                if (hasFocus){
                    view_code_line.setBackgroundColor(getResources().getColor(R.color.black));
                }else{
                    view_code_line.setBackgroundColor(Color.parseColor("#F3F4F4"));
                }
            }
        });

        loginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==11){//判断手机号码是否正确
                    //正则判断手机号码是否合法
                    if (Utils.isMobile(loginPhone.getText().toString().trim())) {
                        if (loginCode.getText().toString().trim().length()==6){
                            loginBtn.setEnabled(true);
                            binding_btn_tv.setTextColor(getResources().getColor(R.color.white));
                            loginBtn.setBackgroundColor(Color.parseColor("#000000"));
                        }

                    }else{
                        toast("请输入正确的手机号码");
                    }

                }else{
                    loginBtn.setEnabled(false);
                    binding_btn_tv.setTextColor(getResources().getColor(R.color.white));
                    loginBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_btn_style)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==6){
                    if (Utils.isMobile(loginPhone.getText().toString().trim())) {
                        loginBtn.setEnabled(true);
                        binding_btn_tv.setTextColor(getResources().getColor(R.color.white));
                        loginBtn.setBackgroundColor(Color.parseColor("#000000"));
                    }

                }else{
                    loginBtn.setEnabled(false);
                    binding_btn_tv.setTextColor(getResources().getColor(R.color.white));
                    loginBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_btn_style)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginBtn.setEnabled(false);

        return view;
    }

    @Override
    public void onEvent() {
        UserInfo login = DateStorage.getInformation();
        loginPhone.setText(login.getUserphone());
        if(loginPhone.getText().length()>0){
            loginPhone.setFocusable(false);
            loginCode.setFocusableInTouchMode(true);
            loginCode.setFocusable(true);
            loginCode.requestFocus();
//            wechat_code_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_selected)));
        }
    }

    @Override
    public void onCustomized() {

    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        // 获取验证码成功回调
        if (message.what == LogicActions.RegisterCodeSuccess) {
            toast(message.obj + "");
        }
        //手机未注册
        if (message.what == LogicActions.Failed) {
            if (message.obj.equals("您的手机还未注册，请重试！")){
                //未绑定
                wxdialog = new WeixinLoginDialog(getActivity(), "");
                wxdialog.show();
            }
        }
        // 登录成功回调
        if (message.what == LogicActions.LoginCodeSuccess) {
            // 隐藏弹窗
            dismissDialog();

            UserInfo login = (UserInfo) message.obj;
            // 储存用户信息
            DateStorage.setInformation(login);
            // 设置登录状态
            DateStorage.setLoginStatus(true);
            // 通知登录成功
            Variable.AuthShowStatus=true;
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
//            if (LoginActivity.relocate){
//                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GoMain"), true, 1);
//            }
            // 关闭登录页面
            ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
            // 关闭页面
            isFinish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.login_access, R.id.login_btn,R.id.wechat_name_layout,R.id.login_phone,R.id.register_agreement_user,R.id.register_agreement_secret})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_access:
                if (TextUtils.isEmpty(loginPhone.getText().toString())) {
                    toast("手机号不能为空");
                } else if (!Utils.isMobile(loginPhone.getText().toString())) {
                    toast("手机号格式不正确，请重新填写");
                } else {
                    // 隐藏输入法
                    Utils.hideSoftInput(getActivity(), null);
                    // 倒计时
                    countDown();
                    // 请求获取验证码
                    paramMap.clear();
                    paramMap.put("userphone", loginPhone.getText().toString());
                    paramMap.put("reqsource", "00");
                    NetworkRequest.getInstance().POST(handler, paramMap, "RegisterCode", HttpCommon.RegisterCode);
                }
                break;
            case R.id.login_btn:
                if (TextUtils.isEmpty(loginPhone.getText().toString())) {
                    toast("手机号不能为空");
                } else if (!Utils.isMobile(loginPhone.getText().toString())) {
                    toast("手机号格式不正确，请重新填写");
                } else if (TextUtils.isEmpty(loginCode.getText().toString())) {
                    toast("验证码不能为空");
                } else {
                    // 请求登录
                    Variable.userPhone=loginPhone.getText().toString();
                    paramMap.clear();
                    paramMap.put("userphone", loginPhone.getText().toString());
                    paramMap.put("smscode", loginCode.getText().toString());
                    NetworkRequest.getInstance().POST(handler, paramMap, "LoginCode", HttpCommon.LoginCode);
                    // 显示弹窗
                    showDialog();
                }
                break;
            case R.id.wechat_name_layout:
            case R.id.login_phone:
                loginPhone.setFocusable(true);
                loginPhone.setFocusableInTouchMode(true);
                loginPhone.requestFocus();
                break;
            case R.id.register_agreement_user:
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Variable.webUrl, Variable.protocl_Agreement_User);
                startActivity(intent);
                break;
            case R.id.register_agreement_secret:
                Intent intent1 = new Intent(getActivity(), WebViewActivity.class);
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

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    // 设置获取验证码按钮不可点击
                    if (loginAccess==null){
                        this.cancel();
                        return;
                    }
                    loginAccess.setEnabled(false);
                    loginAccess.setBackground(getResources().getDrawable((R.drawable.banding_wechat_count_style)));
                    loginAccessText.setText(millisUntilFinished / 1000 + "s");
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }

            @Override
            public void onFinish() {
                try {
                    if (loginAccessText==null){
                     this.cancel();
                    return;
                    }
                    loginAccessText.setText("重新发送");
                    loginAccess.setEnabled(true);
                    loginAccess.setBackground(getResources().getDrawable((R.drawable.banding_wechat_code_style)));
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }
        }.start();
    }

}
