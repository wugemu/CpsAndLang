package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.RetrieveActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;

import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重置密码
 * Created by zyhant on 18-2-11.
 */

public class ResetPasswordFragment extends BaseFragment {

    @BindView(R.id.fragment_reset_password_phone)
    TextView fragmentResetPasswordPhone;
    @BindView(R.id.fragment_reset_password_code)
    EditText fragmentResetPasswordCode;
    @BindView(R.id.fragment_reset_password_code_btn_text)
    TextView fragmentResetPasswordCodeBtnText;
    @BindView(R.id.fragment_reset_password_code_btn)
    LinearLayout fragmentResetPasswordCodeBtn;
    @BindView(R.id.fragment_reset_password_password)
    EditText fragmentResetPasswordPassword;
    @BindView(R.id.fragment_reset_password_confirm)
    EditText fragmentResetPasswordConfirm;
    @BindView(R.id.fragment_reset_password_btn)
    LinearLayout fragmentResetPasswordBtn;
    private String phone;

    public static ResetPasswordFragment getInstance() {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        phone = ((RetrieveActivity) getActivity()).phone;
        fragmentResetPasswordPhone.setText("当前手机号码" + phone.substring(0, 3) + "****" + phone.substring(7));
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
        if (message.what == LogicActions.ResetPasswordSuccess) {
            toast(message.obj + "");
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
                // 设置获取验证码按钮不可点击
                fragmentResetPasswordCodeBtn.setEnabled(false);
                fragmentResetPasswordCodeBtnText.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                fragmentResetPasswordCodeBtnText.setText("获取验证码");
                fragmentResetPasswordCodeBtn.setEnabled(true);
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.fragment_reset_password_code_btn, R.id.fragment_reset_password_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_reset_password_code_btn:
                // 隐藏输入法
                Utils.hideSoftInput(getActivity(), null);
                // 倒计时
                countDown();
                // 请求获取验证码
                paramMap.clear();
                paramMap.put("userphone", phone);
                paramMap.put("reqsource", "00");
                NetworkRequest.getInstance().POST(handler, paramMap, "RegisterCode", HttpCommon.RegisterCode);
                break;
            case R.id.fragment_reset_password_btn:
                if (TextUtils.isEmpty(fragmentResetPasswordCode.getText().toString())) {
                    toast("验证码不能为空");
                } else {
                    if (fragmentResetPasswordCode.getText().length() < 6) {
                        toast("验证码格式不正确，请重新填写");
                    } else {
                        if (TextUtils.isEmpty(fragmentResetPasswordPassword.getText().toString())) {
                            toast("密码不能为空");
                        } else {
                            if (!fragmentResetPasswordPassword.getText().toString().equals(fragmentResetPasswordConfirm.getText().toString())) {
                                toast("两次密码输入不一致");
                            } else {
                                paramMap.clear();
                                paramMap.put("userphone", phone);
                                paramMap.put("userpwd", Utils.getMD5(fragmentResetPasswordPassword.getText().toString()));
                                paramMap.put("smscode", fragmentResetPasswordCode.getText().toString());
                                NetworkRequest.getInstance().POST(handler, paramMap, "ResetPassword", HttpCommon.ResetPassword);
                            }
                        }
                    }
                }
                break;
        }
    }
}
