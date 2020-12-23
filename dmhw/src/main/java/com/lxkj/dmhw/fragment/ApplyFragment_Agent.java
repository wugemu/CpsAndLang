package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.dialog.ApplyDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 升级代理
 * Created by Android on 2018/4/20.
 */

public class ApplyFragment_Agent extends BaseFragment {

    @BindView(R.id.fragment_apply_agent_wechat)
    EditText fragmentApplyAgentWechat;
    @BindView(R.id.fragment_apply_agent_name)
    EditText fragmentApplyAgentName;
    @BindView(R.id.fragment_apply_agent_btn)
    LinearLayout fragmentApplyAgentBtn;
    private String status = "";

    public static ApplyFragment_Agent getInstance() {
        ApplyFragment_Agent fragment = new ApplyFragment_Agent();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_agent, null);
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
        if (message.what == LogicActions.ApplyDialogSuccess) {
            ApplyDialog dialog = new ApplyDialog(getActivity(), false);
            dialog.showDialog();
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        dismissDialog();
        if (message.what == LogicActions.SetAgentSuccess) {
            ApplyDialog dialog = new ApplyDialog(getActivity(), true);
            dialog.showDialog();
        }
    }

    @OnClick(R.id.fragment_apply_agent_btn)
    public void onViewClicked() {
        if (TextUtils.isEmpty(fragmentApplyAgentWechat.getText().toString())) {
            toast("微信号不能位空");
        } else {
            if (TextUtils.isEmpty(fragmentApplyAgentName.getText().toString())) {
                toast("姓名不能为空");
            } else {
                paramMap = new HashMap<>();
                paramMap.put("userid", login.getUserid());
                paramMap.put("username", fragmentApplyAgentName.getText().toString());
                paramMap.put("usersxcode", fragmentApplyAgentWechat.getText().toString());
                paramMap.put("reqdesc", "");
                NetworkRequest.getInstance().POST(handler, paramMap, "SetAgent", HttpCommon.SetAgent);
                showDialog();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
