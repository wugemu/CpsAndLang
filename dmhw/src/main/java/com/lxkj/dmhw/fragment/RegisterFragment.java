package com.lxkj.dmhw.fragment;


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

import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.bean.WeChatUserInfo;
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

/**
 * 注册新用户页
 * Created by Zyhant on 2018/1/16.
 */
public class RegisterFragment extends BaseFragment {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.register_phone)
    EditText registerPhone;
    @BindView(R.id.register_code_btn)
    LinearLayout registerCodeBtn;
    @BindView(R.id.register_code)
    EditText registerCode;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_btn)
    LinearLayout registerBtn;
    @BindView(R.id.register_agreement)
    TextView registerAgreement;
    @BindView(R.id.register_code_btn_text)
    TextView registerCodeBtnText;
    @BindView(R.id.register_btn_tv)
    TextView register_btn_tv;

    @BindView(R.id.register_code_layout)
    LinearLayout registerCodeLayout;
    @BindView(R.id.wechat_name_layout)
    LinearLayout wechat_name_layout;
    @BindView(R.id.wechat_code_layout)
    LinearLayout wechat_code_layout;
    // 手机号
    private String wechatPhone = "";
    private WeChatUserInfo info;
    private boolean isRegister=false;
    public static RegisterFragment getInstance(WeChatUserInfo info,String phone) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("wechat", info);
        bundle.putString("phone",phone);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            info = (WeChatUserInfo) bundle.getSerializable("wechat");
            wechatPhone=bundle.getString("phone");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, null);
        ButterKnife.bind(this, view);
        registerPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    wechat_name_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_selected)));
                }else{
                    wechat_name_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_unselect)));
                }
            }
        });
        registerCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    registerCodeLayout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_selected)));
                }else{
                    registerCodeLayout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_unselect)));
                }
            }
        });
        registerPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    wechat_code_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_selected)));
                }else{
                    wechat_code_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_unselect)));
                }
            }
        });
        registerPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (registerPhone.getText().toString().length()==11){
                    //正则判断手机号码是否合法
                    if (Utils.isMobile(registerPhone.getText().toString().trim())) {
                        if (wechatPhone==null){
                         //正常的注册入口，需要判断验证码
                         if (registerCode.getText().length()==6&&registerPassword.getText().length()>=6){
                             registerBtn.setEnabled(true);
                             register_btn_tv.setTextColor(getResources().getColor(R.color.white));
                             registerBtn.setBackgroundColor(Color.parseColor("#000000"));
                         }
                        }else{//微信登录入口跳转注册页面，不需要判断验证码（因为前一个步骤已经有验证码了）
                            if (registerPassword.getText().length()>=6){
                                registerBtn.setEnabled(true);
                                register_btn_tv.setTextColor(getResources().getColor(R.color.white));
                                registerBtn.setBackgroundColor(Color.parseColor("#000000"));
                            }

                        }
                    }else{
                        toast("请输入正确的手机号码");
                    }

                }else{
                    registerBtn.setEnabled(false);
                    register_btn_tv.setTextColor(getResources().getColor(R.color.white));
                    registerBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_btn_style)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==6){//验证码满足六位
                    if (Utils.isMobile(registerPhone.getText().toString().trim())&&registerPassword.getText().length()>=6) {
                        registerBtn.setEnabled(true);
                        register_btn_tv.setTextColor(getResources().getColor(R.color.white));
                        registerBtn.setBackgroundColor(Color.parseColor("#000000"));
                    }
                }else{
                    registerBtn.setEnabled(false);
                    register_btn_tv.setTextColor(getResources().getColor(R.color.white));
                    registerBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_btn_style)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (registerPassword.getText().length()>= 6) {
                    if (wechatPhone==null) {
                        if (Utils.isMobile(registerPhone.getText().toString().trim()) && registerCode.getText().length() == 6) {
                            registerBtn.setEnabled(true);
                            register_btn_tv.setTextColor(getResources().getColor(R.color.white));
                            registerBtn.setBackgroundColor(Color.parseColor("#000000"));
                        }
                    } else {
                        if (Utils.isMobile(registerPhone.getText().toString().trim())) {
                            registerBtn.setEnabled(true);
                            register_btn_tv.setTextColor(getResources().getColor(R.color.white));
                            registerBtn.setBackgroundColor(Color.parseColor("#000000"));
                        }
                    }
                }
                else{
                    registerBtn.setEnabled(false);
                    register_btn_tv.setTextColor(getResources().getColor(R.color.white));
                    registerBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_btn_style)));
                }
                }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerBtn.setEnabled(false);
        return view;
    }

    @Override
    public void onEvent() {
            if (wechatPhone!=null) {
                registerPhone.setText(wechatPhone);
                registerPhone.setEnabled(false);
                registerCodeBtn.setVisibility(View.GONE);
                registerCodeLayout.setVisibility(View.GONE);
                registerPassword.setFocusableInTouchMode(true);
                registerPassword.setFocusable(true);
                registerPassword.requestFocus();
                wechat_code_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_selected)));
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
        // 提交成功回调
        if (message.what == LogicActions.RegisterSuccess) {
            // 弹出提示
            toast(message.obj + "");
            isRegister=true;
            // 请求登录
            paramMap.clear();
            paramMap.put("userphone", registerPhone.getText().toString());
            paramMap.put("userpwd", Utils.getMD5(registerPassword.getText().toString()));
            NetworkRequest.getInstance().POST(handler, paramMap, "Login", HttpCommon.Login);
        }
        if (message.what == LogicActions.RegisterWeChatSuccess) {
            // 隐藏弹窗
            dismissDialog();
            UserInfo login = (UserInfo) message.obj;
            // 储存用户信息
            DateStorage.setInformation(login);
            // 设置登录状态
            DateStorage.setLoginStatus(true);
            // 通知登录成功
            Variable.AuthShowStatus=true;//不让授权登录窗口显示
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
            //注册成功，奖励金币弹框
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RegisterStatus"), true, 0);
            // 关闭登录页面
            ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
            // 关闭页面
            isFinish();
        }
        // 登录成功回调
        if (message.what == LogicActions.LoginSuccess) {
            // 隐藏弹窗
            dismissDialog();
            UserInfo login = (UserInfo) message.obj;
            // 储存用户信息
            DateStorage.setInformation(login);
            // 设置登录状态
            DateStorage.setLoginStatus(true);
            // 通知登录成功
            Variable.AuthShowStatus=true;//不让授权登录窗口显示
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);

            if (isRegister){
                //注册成功，奖励金币弹框
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RegisterStatus"), true, 0);
            }
            // 关闭登录页面
            ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
            // 关闭页面
            isFinish();
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
                    registerCodeBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_count_style)));
                    registerCodeBtn.setEnabled(false);
                    registerCodeBtnText.setText(millisUntilFinished / 1000 + "s");
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }

            @Override
            public void onFinish() {
                try {
                    if (registerCodeBtn!=null) {
                        registerCodeBtn.setBackground(getResources().getDrawable((R.drawable.banding_wechat_code_style)));
                        registerCodeBtnText.setText("重新发送");
                        registerCodeBtn.setEnabled(true);
                    }
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }
        }.start();
    }

    @OnClick({R.id.back, R.id.register_code_btn, R.id.register_btn, R.id.register_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:// 返回
                isFinish();
                break;
            case R.id.register_code_btn:// 获取验证码
                // 判断手机号是否为空
                if (TextUtils.isEmpty(registerPhone.getText().toString())) {
                    toast("手机号不能为空");
                } else {
                    // 判断手机号格式是否正确
                    if (Utils.isMobile(registerPhone.getText().toString())) {
                        // 隐藏输入法
                        Utils.hideSoftInput(getActivity(), null);
                        // 倒计时
                        countDown();
                        // 请求获取验证码
                        paramMap.clear();
                        paramMap.put("userphone", registerPhone.getText().toString());
                        paramMap.put("extensionid", getActivity().getIntent().getExtras().getString("invitation"));
                        paramMap.put("reqsource", "00");
                        NetworkRequest.getInstance().POST(handler, paramMap, "RegisterCode", HttpCommon.RegisterCode);
                    } else {
                        toast("手机号格式不正确，请重新填写");
                    }
                }
                break;
            case R.id.register_btn:// 注册
                // 判断手机号是否为空
                if (TextUtils.isEmpty(registerPhone.getText().toString())) {
                    toast("手机号不能为空");
                } else {
                    // 判断手机号格式是否正确
                    if (Utils.isMobile(registerPhone.getText().toString())) {
                        if (wechatPhone!= null) {
                            // 判断密码是否为空
                            if (TextUtils.isEmpty(registerPassword.getText().toString())) {
                                toast("密码不能为空");
                            } else {
                                    // 显示弹窗
                                    showDialog();
                                    // 请求注册
                                    paramMap.clear();
                                    paramMap.put("userid", login.getUserid());
                                    paramMap.put("extensionid", getActivity().getIntent().getExtras().getString("invitation"));
                                    paramMap.put("userphone", registerPhone.getText().toString());
                                    paramMap.put("userpwd", Utils.getMD5(registerPassword.getText().toString()));
                                    paramMap.put("wxuuid", info.getUnionid());
                                    paramMap.put("wxmc", info.getNickname());
                                    paramMap.put("wxhead", info.getHeadimgurl());
                                    NetworkRequest.getInstance().POST(handler, paramMap, "RegisterWeChat", HttpCommon.RegisterWeChat);

                            }
                        } else {
                            // 判断验证码是否为空
                            if (TextUtils.isEmpty(registerCode.getText().toString())) {
                                toast("验证码不能为空");
                            } else {
                                // 判断验证码格式
                                if (registerCode.getText().length() < 6) {
                                    toast("验证码格式不正确，请重新填写");
                                } else {
                                    // 判断密码是否为空
                                    if (TextUtils.isEmpty(registerPassword.getText().toString())) {
                                        toast("密码不能为空");
                                    } else {
                                            // 显示弹窗
                                            showDialog();
                                            // 请求注册
                                            paramMap.clear();
                                            paramMap.put("userid", login.getUserid());
                                            paramMap.put("extensionid", getActivity().getIntent().getExtras().getString("invitation"));
                                            paramMap.put("userphone", registerPhone.getText().toString());
                                            paramMap.put("userpwd", Utils.getMD5(registerPassword.getText().toString()));
                                            paramMap.put("smscode", registerCode.getText().toString());
                                            NetworkRequest.getInstance().POST(handler, paramMap, "Register", HttpCommon.Register);
                                    }
                                }
                            }
                        }
                    } else {
                        toast("手机号格式不正确，请重新填写");
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

//    private void initLoginState(Message message){
//            // 登录成功回调
//            if (message.what == LogicActions.LoginSuccess) {
//                // 隐藏弹窗
//                dismissDialog();
//                UserInfo login = (UserInfo) message.obj;
//                // 储存用户信息
//                DateStorage.setInformation(login);
//                // 设置登录状态
//                DateStorage.setLoginStatus(true);
//                // 通知登录成功
//                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
//                // 关闭登录页面
//                ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
//                // 关闭页面
//                isFinish();
//            }
//    }


}
