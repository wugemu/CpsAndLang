package com.lxkj.dmhw.fragment;


import android.content.Intent;
import android.os.Bundle;
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
 * 修改密码
 * Created by Zyhant on 2018/1/16.
 */
public class PasswordFragment extends BaseFragment {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.fragment_password_original)
    EditText fragmentPasswordOriginal;
    @BindView(R.id.fragment_password_new)
    EditText fragmentPasswordNew;
    @BindView(R.id.fragment_password_confirm)
    EditText fragmentPasswordConfirm;
    @BindView(R.id.fragment_password_btn)
    LinearLayout fragmentPasswordBtn;
    @BindView(R.id.fragment_password_not)
    TextView fragmentPasswordNot;

    public static PasswordFragment getInstance() {
        PasswordFragment fragment = new PasswordFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {

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
        dismissDialog();
        if (message.what == LogicActions.ModifyPasswordSuccess) {
            if (message.obj.equals("设置成功!")) {
                isFinish();
                toast("修改成功");
            } else {
                toast(message.obj + "");
            }
        }
    }

    @OnClick({R.id.back, R.id.fragment_password_btn, R.id.fragment_password_not})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.fragment_password_not:
                intent = new Intent(getActivity(), RetrieveActivity.class);
                break;
            case R.id.fragment_password_btn:
                if (TextUtils.isEmpty(fragmentPasswordOriginal.getText().toString())) {
                    toast("原密码不能为空");
                } else {
                    if (TextUtils.isEmpty(fragmentPasswordNew.getText().toString())) {
                        toast("新密码不能为空");
                    } else {
                        if (!fragmentPasswordNew.getText().toString().equals(fragmentPasswordConfirm.getText().toString())) {
                            toast("两次密码输入不一致");
                        } else {
                            paramMap = new HashMap<>();
                            paramMap.put("userid", login.getUserid());
                            paramMap.put("userpwd", Utils.getMD5(fragmentPasswordOriginal.getText().toString()));
                            paramMap.put("usernewpwd", Utils.getMD5(fragmentPasswordNew.getText().toString()));
                            NetworkRequest.getInstance().POST(handler, paramMap, "ModifyPassword", HttpCommon.ModifyPassword);
                            showDialog();
                        }
                    }
                }
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
