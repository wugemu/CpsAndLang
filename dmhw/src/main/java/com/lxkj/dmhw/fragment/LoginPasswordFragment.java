package com.lxkj.dmhw.fragment;

import android.graphics.Color;
import android.os.Bundle;
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
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.bean.UserInfo;
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

public class LoginPasswordFragment extends BaseFragment {

    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_btn)
    LinearLayout loginBtn;

    @BindView(R.id.binding_btn_tv)
    TextView binding_btn_tv;

    @BindView(R.id.wechat_name_layout)
    LinearLayout wechat_name_layout;
    @BindView(R.id.view_phone_line)
    View view_phone_line;
    @BindView(R.id.wechat_code_layout)
    LinearLayout wechat_code_layout;
    @BindView(R.id.view_pwd_line)
    View view_pwd_line;
    public static LoginPasswordFragment getInstance() {
        LoginPasswordFragment fragment = new LoginPasswordFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_password, null);
        ButterKnife.bind(this, view);


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
        loginPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (view_pwd_line==null){
                    return;
                }
                if (hasFocus){
                    view_pwd_line.setBackgroundColor(getResources().getColor(R.color.black));
                }else{
                    view_pwd_line.setBackgroundColor(Color.parseColor("#F3F4F4"));
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
                      if (loginPassword.getText().length()>0){
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

        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
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
            loginPassword.setFocusableInTouchMode(true);
            loginPassword.setFocusable(true);
            loginPassword.requestFocus();
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
            Variable.AuthShowStatus=true;
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
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

    @OnClick({R.id.login_btn,R.id.wechat_name_layout,R.id.login_phone})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.login_btn :
                // 判断手机是否为空
                if (TextUtils.isEmpty(loginPhone.getText().toString())) {
                    toast("手机号不能为空");
                } else {
                    // 判断手机格式是否正确
                    if (Utils.isMobile(loginPhone.getText().toString())) {
                        // 判断密码是否为空
                        if (TextUtils.isEmpty(loginPassword.getText().toString())) {
                            toast("密码不能为空");
                        } else {
                            // 请求登录
                            paramMap = new HashMap<>();
                            paramMap.put("userphone", loginPhone.getText().toString());
                            paramMap.put("userpwd", Utils.getMD5(loginPassword.getText().toString()));
                            NetworkRequest.getInstance().POST(handler, paramMap, "Login", HttpCommon.Login);
                            // 显示弹窗
                            showDialog();
                        }
                    } else {
                        toast("手机号格式不正确，请重新填写");
                    }
                }
                break;

            case R.id.wechat_name_layout:
                loginPhone.setFocusable(true);
                loginPhone.setFocusableInTouchMode(true);
                loginPhone.requestFocus();
                break;
            case R.id.login_phone:
                loginPhone.setFocusable(true);
                loginPhone.setFocusableInTouchMode(true);
                loginPhone.requestFocus();
                break;
        }


    }
}
