package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * 忘记密码输入手机号
 * Created by zyhant on 18-2-7.
 */

public class RetrievePhoneFragment extends BaseFragment {

    @BindView(R.id.fragment_retrieve_phone_edit)
    EditText fragmentRetrievePhoneEdit;
    @BindView(R.id.fragment_retrieve_phone_not)
    TextView fragmentRetrievePhoneNot;
    @BindView(R.id.fragment_retrieve_phone_btn)
    LinearLayout fragmentRetrievePhoneBtn;
    @BindView(R.id.fragment_retrieve_cancel)
    LinearLayout fragmentRetrieveCancel;
    @BindView(R.id.fragment_retrieve_service)
    Button fragmentRetrieveService;
    @BindView(R.id.fragment_retrieve_no)
    Button fragmentRetrieveNo;
    @BindView(R.id.fragment_retrieve_layout)
    LinearLayout fragmentRetrieveLayout;
    private String qq;

    public static RetrievePhoneFragment getInstance() {
        RetrievePhoneFragment fragment = new RetrievePhoneFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retrieve_phone, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 获取QQ客服
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "QQService", HttpCommon.QQService);
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
        if (message.what == LogicActions.ExistencePhoneSuccess) {
            if ((Boolean) message.obj) {
                ((RetrieveActivity) getActivity()).resetPassword();
                ((RetrieveActivity) getActivity()).phone = fragmentRetrievePhoneEdit.getText().toString();
            } else {
                toast("该手机号还没有注册哦");
            }
        }
        if (message.what == LogicActions.QQServiceSuccess) {
            qq = message.obj + "";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.fragment_retrieve_phone_not, R.id.fragment_retrieve_phone_btn, R.id.fragment_retrieve_cancel, R.id.fragment_retrieve_service, R.id.fragment_retrieve_no})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.fragment_retrieve_phone_not:
                fragmentRetrieveLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.fragment_retrieve_phone_btn:
                if (TextUtils.isEmpty(fragmentRetrievePhoneEdit.getText().toString())) {
                    toast("手机号不能为空");
                } else {
                    if (!Utils.isMobile(fragmentRetrievePhoneEdit.getText().toString())) {
                        toast("手机号格式不正确，请重新填写");
                    } else {
                        paramMap = new HashMap<>();
                        paramMap.put("userphone", fragmentRetrievePhoneEdit.getText().toString());
                        NetworkRequest.getInstance().POST(handler, paramMap, "ExistencePhone", HttpCommon.ExistencePhone);
                    }
                }
                break;
            case R.id.fragment_retrieve_cancel:
                fragmentRetrieveLayout.setVisibility(View.GONE);
                break;
            case R.id.fragment_retrieve_service:
                Utils.chatQQ(qq);
                break;
            case R.id.fragment_retrieve_no:
                fragmentRetrieveLayout.setVisibility(View.GONE);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
